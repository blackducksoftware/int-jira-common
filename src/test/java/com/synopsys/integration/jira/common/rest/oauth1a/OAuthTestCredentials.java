package com.synopsys.integration.jira.common.rest.oauth1a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.JiraServerServiceTestUtility;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;
import com.synopsys.integration.log.Slf4jIntLogger;

public class OAuthTestCredentials {
    public static final Logger logger = LoggerFactory.getLogger(OAuthTestCredentials.class);

    private static final TestProperties testProperties = new TestProperties();

    private String baseUrl;
    private String consumerKey;
    private String privateKey;
    private String temporaryToken;
    private String verificationCode;
    private String accessToken;
    private final JiraHttpClient jiraHttpClient;
    private boolean isCloud;

    public static OAuthTestCredentials fromCloud() {
        return new OAuthTestCredentials(
            testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_URL),
            JiraOauthTestUtility.getConsumerKey(),
            JiraOauthTestUtility.getPrivateKey(),
            testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_OAUTH_ACCESS_TOKEN),
            JiraCloudServiceTestUtility.createJiraCredentialClient(new Slf4jIntLogger(logger)),
            true
        );
    }

    public static OAuthTestCredentials fromServer() {
        return new OAuthTestCredentials(
            testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL),
            JiraOauthTestUtility.getConsumerKey(),
            JiraOauthTestUtility.getPrivateKey(),
            testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_OAUTH_ACCESS_TOKEN),
            JiraServerServiceTestUtility.createJiraCredentialClient(new Slf4jIntLogger(logger)),
            false
        );
    }

    public OAuthTestCredentials(
        String baseUrl,
        String consumerKey,
        String privateKey,
        String accessToken,
        JiraHttpClient jiraHttpClient,
        boolean isCloud
    ) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.privateKey = privateKey;
        this.accessToken = accessToken;
        this.jiraHttpClient = jiraHttpClient;
        this.isCloud = isCloud;
    }

    public OAuthTestCredentials(
        String baseUrl,
        String consumerKey,
        String privateKey,
        String temporaryToken,
        String verificationCode,
        String accessToken,
        boolean isCloud,
        JiraHttpClient jiraHttpClient
    ) {
        this(baseUrl, consumerKey, privateKey, accessToken, jiraHttpClient, isCloud);
        this.temporaryToken = temporaryToken;
        this.verificationCode = verificationCode;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTemporaryToken() {
        return temporaryToken;
    }

    public void setTemporaryToken(String temporaryToken) {
        this.temporaryToken = temporaryToken;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public JiraHttpClient getJiraHttpClient() {
        return jiraHttpClient;
    }

    public boolean isCloud() {
        return isCloud;
    }

    public void setCloud(boolean cloud) {
        isCloud = cloud;
    }
}
