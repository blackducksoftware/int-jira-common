package com.synopsys.integration.jira.common.rest.oauth1a;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.synopsys.integration.jira.common.JiraTestConstants;
import com.synopsys.integration.jira.common.model.oauth.OAuthCredentialsData;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClientFactory;

public final class JiraOauthTestUtility {

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
        return getEnvVarAndAssumeTrue(JiraTestConstants.CONSUMER_KEY);
    }

    public static String getPrivateKey() {
        return getEnvVarAndAssumeTrue(JiraTestConstants.PRIVATE_KEY);
    }

    public static String getTemporaryToken() {
        return getEnvVarAndAssumeTrue(JiraTestConstants.TEMPORARY_TOKEN);
    }

    public static String getVerificationCode() {
        return getEnvVarAndAssumeTrue(JiraTestConstants.VERIFICATION_CODE);
    }

    public static String getEnvVarAndAssumeTrue(String envVarName) {
        String envVar = System.getenv(envVarName);
        assumeTrue(null != envVar, "Missing value for " + envVarName);
        return envVar;
    }
}
