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

    public static JiraHttpClient createOAuthClient() throws NoSuchAlgorithmException, InvalidKeySpecException {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(getBaseUrl());
        OAuthCredentialsData oAuthCredentialsData = new OAuthCredentialsData(getPrivateKey(), getConsumerKey(), getAccessToken());
        OAuthParameters oAuthParameters = jiraOAuthService.createOAuthParameters(oAuthCredentialsData);
        return new JiraHttpClientFactory().createJiraOAuthClient(getBaseUrl(), oAuthParameters);
    }

    public static String getBaseUrl() {
        return getEnvVarAndAssumeTrue(JiraTestConstants.CLOUD_BASE_URL);
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

    public static String getAccessToken() {
        return getEnvVarAndAssumeTrue(JiraTestConstants.ACCESS_TOKEN);
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
