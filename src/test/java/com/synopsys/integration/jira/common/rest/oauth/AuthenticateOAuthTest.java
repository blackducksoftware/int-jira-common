package com.synopsys.integration.jira.common.rest.oauth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTest;
import com.synopsys.integration.jira.common.cloud.service.ProjectService;
import com.synopsys.integration.jira.common.model.oauth.OAuthAuthorizationData;
import com.synopsys.integration.jira.common.model.oauth.OAuthCredentialsData;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClientFactory;
import com.synopsys.integration.jira.common.rest.service.CommonServiceFactory;
import com.synopsys.integration.jira.common.rest.service.JiraService;
import com.synopsys.integration.log.Slf4jIntLogger;

public class AuthenticateOAuthTest extends JiraCloudServiceTest {
    public static final String CONSUMER_KEY = "JIRA_OAUTH_CONSUMER_KEY";
    public static final String PRIVATE_KEY = "JIRA_OAUTH_PRIVATE_KEY";
    public static final String TEMPORARY_TOKEN = "JIRA_OAUTH_TEMP_TOKEN";
    public static final String ACCESS_TOKEN = "JIRA_OAUTH_ACCESS_TOKEN";
    public static final String VERIFICATION_CODE = "JIRA_OAUTH_VERIFICATION_CODE";

    /*
     * These tests shouldn't be ran if the env vars are not found in the env. If you don't have all
     * the env vars available or have not yet setup OAuth with the target server, run each test one at
     * a time starting from 'createAuthUrl' then to 'createAuthenticationToken' then finally testing
     * your credentials by running 'verifyConnection'. The values to put in you env vars will be printed
     * to console.
     */

    @Test
    @Ignore("Be sure you have all variables available before running")
    public void createAuthUrl() throws Exception {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        String jiraUrl = getEnvVarAndAssumeTrue(JiraCloudServiceTest.ENV_BASE_URL);
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(jiraUrl);

        String consumerKey = getEnvVarAndAssumeTrue(CONSUMER_KEY);
        String privateKey = getEnvVarAndAssumeTrue(PRIVATE_KEY);
        OAuthAuthorizationData oAuthAuthorizationData = jiraOAuthService.createOAuthAuthorizationData(consumerKey, privateKey);

        String authorizationToken = oAuthAuthorizationData.getAuthorizationToken();
        assertTrue(StringUtils.isNotBlank(authorizationToken));

        String authorizationUrl = oAuthAuthorizationData.getAuthorizationUrl();
        URL url = new URL(authorizationUrl);
        assertTrue(StringUtils.isNotBlank(url.toString()));

        System.out.println(authorizationToken);
        System.out.println(authorizationUrl);
    }

    @Test
    @Ignore("Be sure you have all variables available before running")
    public void createAuthenticationToken() throws Exception {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        String jiraUrl = getEnvVarAndAssumeTrue(JiraCloudServiceTest.ENV_BASE_URL);
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(jiraUrl);

        String temporaryToken = getEnvVarAndAssumeTrue(TEMPORARY_TOKEN);
        String verificationCode = getEnvVarAndAssumeTrue(VERIFICATION_CODE);
        String consumerKey = getEnvVarAndAssumeTrue(CONSUMER_KEY);
        String privateKey = getEnvVarAndAssumeTrue(PRIVATE_KEY);
        JiraOAuthGetAccessToken jiraOAuthGetAccessToken = jiraOAuthService.getJiraOAuthGetAccessToken(temporaryToken, verificationCode, consumerKey, privateKey);
        OAuthCredentialsResponse oAuthCredentialsResponse = jiraOAuthGetAccessToken.execute();

        assertNotNull(oAuthCredentialsResponse);
        String token = oAuthCredentialsResponse.token;
        assertTrue(StringUtils.isNotBlank(token));
        System.out.println(token);
    }

    @Test
    @Ignore("Be sure you have all variables available before running")
    public void verifyConnection() throws Exception {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        String jiraUrl = getEnvVarAndAssumeTrue(JiraCloudServiceTest.ENV_BASE_URL);
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(jiraUrl);

        String accessToken = getEnvVarAndAssumeTrue(ACCESS_TOKEN);
        String consumerKey = getEnvVarAndAssumeTrue(CONSUMER_KEY);
        String privateKey = getEnvVarAndAssumeTrue(PRIVATE_KEY);
        OAuthCredentialsData oAuthCredentialsData = new OAuthCredentialsData(privateKey, consumerKey, accessToken);
        OAuthParameters oAuthParameters = jiraOAuthService.createOAuthParameters(oAuthCredentialsData);

        JiraHttpClientFactory jiraHttpClientFactory = new JiraHttpClientFactory();
        JiraHttpClient jiraHttpService = jiraHttpClientFactory.createJiraOAuthClient(jiraUrl, oAuthParameters);
        assertProjectsFound(jiraHttpService);

        Logger logger = LoggerFactory.getLogger(AuthenticateOAuthTest.class);
        JiraHttpClient jiraCredentialClient = createJiraServerConfig().createJiraHttpClient(new Slf4jIntLogger(logger));
        assertProjectsFound(jiraCredentialClient);
    }

    private void assertProjectsFound(JiraHttpClient jiraHttpClient) throws IntegrationException {
        CommonServiceFactory commonServiceFactory = new CommonServiceFactory(null, jiraHttpClient, new Gson());
        JiraService jiraService = commonServiceFactory.createJiraService();

        ProjectService oAuthProjectService = new ProjectService(jiraService);
        PageOfProjectsResponseModel projects = oAuthProjectService.getProjects();

        assertTrue(projects.getTotal() > 0);
    }

}
