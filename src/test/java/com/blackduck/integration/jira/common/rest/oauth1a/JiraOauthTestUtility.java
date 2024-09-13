package com.blackduck.integration.jira.common.rest.oauth1a;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.blackduck.integration.jira.common.model.oauth.OAuthCredentialsData;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.JiraHttpClientFactory;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.google.api.client.auth.oauth.OAuthParameters;

public final class JiraOauthTestUtility {
    private static final TestProperties testProperties = TestProperties.loadTestProperties();

    public static JiraHttpClient createOAuthClient(String baseUrl, String accessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createOAuthClient(baseUrl, getPrivateKey(), getConsumerKey(), accessToken);
    }

    public static JiraHttpClient createOAuthClient(OAuthTestCredentials oAuthTestCredentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createOAuthClient(oAuthTestCredentials.getBaseUrl(), oAuthTestCredentials.getPrivateKey(), oAuthTestCredentials.getConsumerKey(), oAuthTestCredentials.getAccessToken());
    }

    public static JiraHttpClient createOAuthClient(String baseUrl, String privateKey, String consumerKey, String accessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(baseUrl);
        OAuthCredentialsData oAuthCredentialsData = new OAuthCredentialsData(privateKey, consumerKey, accessToken);
        OAuthParameters oAuthParameters = jiraOAuthService.createOAuthParameters(oAuthCredentialsData);
        return new JiraHttpClientFactory().createJiraOAuthClient(baseUrl, oAuthParameters);
    }

    public static String getConsumerKey() {
        return testProperties.getProperty(TestPropertyKey.TEST_JIRA_OAUTH_CONSUMER_KEY);
    }

    public static String getPrivateKey() {
        return testProperties.getProperty(TestPropertyKey.TEST_JIRA_OAUTH_PRIVATE_KEY);
    }

    public static String getTemporaryToken() {
        return testProperties.getProperty(TestPropertyKey.TEST_JIRA_OAUTH_TEMP_TOKEN);
    }

    public static String getVerificationCode() {
        return testProperties.getProperty(TestPropertyKey.TEST_JIRA_OAUTH_VERIFICATION_CODE);
    }
}
