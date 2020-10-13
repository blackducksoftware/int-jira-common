package com.synopsys.integration.jira.common.rest.oauth1a;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.IntegrationsTestConstants;
import com.synopsys.integration.jira.common.cloud.service.ProjectService;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.oauth.OAuthAuthorizationData;
import com.synopsys.integration.jira.common.model.oauth.OAuthCredentialsData;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClientFactory;
import com.synopsys.integration.jira.common.rest.service.CommonServiceFactory;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;

@Tag(IntegrationsTestConstants.INTEGRATION_TEST)
public class AuthenticateOAuthTest {
    private static final OAuthTestCredentials cloudOAuth = OAuthTestCredentials.fromCloud();
    private static final OAuthTestCredentials serverOAuth = OAuthTestCredentials.fromServer();

    // Set your current credentials to use the proper jira instance.
    private final OAuthTestCredentials currentOAuthCredentials = cloudOAuth;

    /*
     * These first two tests shouldn't be ran unless setting up OAuth. If you don't have all
     * the env vars available or have not yet setup OAuth with the target server, run each test one at
     * a time starting from 'createAuthUrl' then to 'createAuthenticationToken' then finally testing
     * your credentials by running 'verifyConnection'. The values to put in you env vars will be printed
     * to console.
     */

    @Test
    @Disabled("Be sure you have all variables available before running")
    public void createAuthUrl() throws Exception {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(currentOAuthCredentials.getBaseUrl());

        OAuthAuthorizationData oAuthAuthorizationData = jiraOAuthService.createOAuthAuthorizationData(currentOAuthCredentials.getConsumerKey(), currentOAuthCredentials.getPrivateKey());

        String authorizationToken = oAuthAuthorizationData.getAuthorizationToken();
        assertTrue(StringUtils.isNotBlank(authorizationToken));

        String authorizationUrl = oAuthAuthorizationData.getAuthorizationUrl();
        URL url = new URL(authorizationUrl);
        assertTrue(StringUtils.isNotBlank(url.toString()));

        System.out.println("Please copy this temporary token into the authCredentials: " + authorizationToken);
        System.out.println("Please follow this link and copy the verification code into the authCredentials: " + authorizationUrl);
    }

    // Set the two printed values here
    private void setTemporaryTokenAndVerificationCode() throws Exception {
        String temporaryToken = "";
        String verificationCode = "";

        if (StringUtils.isBlank(temporaryToken) || StringUtils.isBlank(verificationCode)) {
            throw new Exception("Must set temporaryToken and verificationCode");
        }

        currentOAuthCredentials.setTemporaryToken(temporaryToken);
        currentOAuthCredentials.setVerificationCode(verificationCode);
    }

    @Test
    @Disabled("Be sure you have all variables available before running")
    public void createAuthenticationToken() throws Exception {
        setTemporaryTokenAndVerificationCode();
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(currentOAuthCredentials.getBaseUrl());

        JiraOAuthGetAccessToken jiraOAuthGetAccessToken = jiraOAuthService.getJiraOAuthGetAccessToken(
            currentOAuthCredentials.getTemporaryToken(),
            currentOAuthCredentials.getVerificationCode(),
            currentOAuthCredentials.getConsumerKey(),
            currentOAuthCredentials.getPrivateKey()
        );
        OAuthCredentialsResponse oAuthCredentialsResponse = jiraOAuthGetAccessToken.execute();

        assertNotNull(oAuthCredentialsResponse);
        String token = oAuthCredentialsResponse.token;
        assertTrue(StringUtils.isNotBlank(token));
        System.out.println("Set this access token in the appropriate oauth env var: " + token);
    }

    // Add the new access token here if you don't want to use the env var (Should normally add the token to env var)
    private void setAccessTokenIfProvided() {
        String accessToken = "";
        if (StringUtils.isNotBlank(accessToken)) {
            currentOAuthCredentials.setAccessToken(accessToken);
        }
    }

    @Test
    // This test will fail if you haven't set up a proper OAuth authentication with our Jira instance.
    public void verifyConnection() throws Exception {
        setAccessTokenIfProvided();
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        String jiraUrl = currentOAuthCredentials.getBaseUrl();
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(jiraUrl);

        OAuthCredentialsData oAuthCredentialsData = new OAuthCredentialsData(
            currentOAuthCredentials.getPrivateKey(),
            currentOAuthCredentials.getConsumerKey(),
            currentOAuthCredentials.getAccessToken()
        );
        OAuthParameters oAuthParameters = jiraOAuthService.createOAuthParameters(oAuthCredentialsData);

        JiraHttpClientFactory jiraHttpClientFactory = new JiraHttpClientFactory();
        JiraHttpClient jiraHttpClient = jiraHttpClientFactory.createJiraOAuthClient(jiraUrl, oAuthParameters);
        assertProjectsFound(jiraHttpClient);

        JiraHttpClient jiraCredentialClient = currentOAuthCredentials.getJiraHttpClient();
        assertProjectsFound(jiraCredentialClient);

        // TODO should this check cloud and Server every time?
    }

    private void assertProjectsFound(JiraHttpClient jiraHttpClient) throws IntegrationException {
        CommonServiceFactory commonServiceFactory = new CommonServiceFactory(null, jiraHttpClient, new Gson());
        JiraApiClient jiraApiClient = commonServiceFactory.getJiraApiClient();
        String failureMessage = (jiraHttpClient instanceof JiraOAuthHttpClient) ? "Have you setup OAuth for the server " + jiraHttpClient.getBaseUrl() : "";
        if (currentOAuthCredentials.isCloud()) {
            ProjectService projectService = new ProjectService(jiraApiClient);
            PageOfProjectsResponseModel projects = projectService.getProjects();
            assertTrue(projects.getTotal() > 0, failureMessage);
        } else {
            com.synopsys.integration.jira.common.server.service.ProjectService projectService = new com.synopsys.integration.jira.common.server.service.ProjectService(jiraApiClient);
            List<ProjectComponent> projects = projectService.getProjects();
            assertTrue(projects.size() > 0, failureMessage);
        }

    }

}
