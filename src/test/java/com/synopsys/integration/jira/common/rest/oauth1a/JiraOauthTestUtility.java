package com.synopsys.integration.jira.common.rest.oauth1a;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.model.oauth.OAuthCredentialsData;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClientFactory;

public final class JiraOauthTestUtility {
    public static final String CONSUMER_KEY = "JIRA_OAUTH_CONSUMER_KEY";
    public static final String PRIVATE_KEY = "JIRA_OAUTH_PRIVATE_KEY";
    public static final String TEMPORARY_TOKEN = "JIRA_OAUTH_TEMP_TOKEN";
    public static final String ACCESS_TOKEN = "JIRA_OAUTH_ACCESS_TOKEN";
    public static final String VERIFICATION_CODE = "JIRA_OAUTH_VERIFICATION_CODE";

    public static JiraHttpClient createOAuthClient() throws NoSuchAlgorithmException, InvalidKeySpecException {
        JiraOAuthServiceFactory jiraOAuthServiceFactory = new JiraOAuthServiceFactory();
        JiraOAuthService jiraOAuthService = jiraOAuthServiceFactory.fromJiraServerUrl(getBaseUrl());
        OAuthCredentialsData oAuthCredentialsData = new OAuthCredentialsData(getPrivateKey(), getConsumerKey(), getAccessToken());
        OAuthParameters oAuthParameters = jiraOAuthService.createOAuthParameters(oAuthCredentialsData);
        return new JiraHttpClientFactory().createJiraOAuthClient(getBaseUrl(), oAuthParameters);
    }

    public static String getBaseUrl() {
        return getEnvVarAndAssumeTrue(JiraCloudServiceTestUtility.ENV_BASE_URL);
    }

    public static String getConsumerKey() {
        return getEnvVarAndAssumeTrue(CONSUMER_KEY);
    }

    public static String getPrivateKey() {
        return getEnvVarAndAssumeTrue(PRIVATE_KEY);
    }

    public static String getTemporaryToken() {
        return getEnvVarAndAssumeTrue(TEMPORARY_TOKEN);
    }

    public static String getAccessToken() {
        return getEnvVarAndAssumeTrue(ACCESS_TOKEN);
    }

    public static String getVerificationCode() {
        return getEnvVarAndAssumeTrue(VERIFICATION_CODE);
    }

    public static String getEnvVarAndAssumeTrue(String envVarName) {
        String envVar = System.getenv(envVarName);
        assumeTrue(null != envVar, "Missing value for " + envVarName);
        return envVar;
    }
}
