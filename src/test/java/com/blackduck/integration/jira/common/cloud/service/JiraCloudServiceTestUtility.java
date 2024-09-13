package com.blackduck.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackduck.integration.jira.common.cloud.configuration.JiraCloudRestConfig;
import com.blackduck.integration.jira.common.cloud.configuration.JiraCloudRestConfigBuilder;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;
import com.blackduck.integration.log.Slf4jIntLogger;
import com.google.gson.Gson;

public final class JiraCloudServiceTestUtility {
    private static final TestProperties testProperties = TestProperties.loadTestProperties();

    public static void validateConfiguration() {
        String baseUrl = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_URL);
        String userEmail = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_EMAIL);
        String apiToken = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_API_TOKEN);
        String testProject = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_TEST_PROJECT_NAME);

        assumeTrue(null != baseUrl, "No Jira Cloud base url provided");
        assumeTrue(null != userEmail, "No Jira Cloud user email provided");
        assumeTrue(null != apiToken, "No Jira Cloud API Token provided");
        assumeTrue(null != testProject, "No Jira Cloud Test project provided");
    }

    public static JiraHttpClient createJiraCredentialClient() {
        Logger logger = LoggerFactory.getLogger(JiraCloudServiceTestUtility.class);
        return createJiraCredentialClient(new Slf4jIntLogger(logger));
    }

    public static JiraHttpClient createJiraCredentialClient(IntLogger logger) {
        return createJiraServerConfig().createJiraHttpClient(logger);
    }

    public static JiraCloudRestConfig createJiraServerConfig() {
        JiraCloudRestConfigBuilder builder = JiraCloudRestConfig.newBuilder();
        builder.setUrl(testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_URL))
            .setAuthUserEmail(testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_EMAIL))
            .setApiToken(testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_API_TOKEN));
        return builder.build();
    }

    public static JiraCloudServiceFactory createServiceFactory(JiraHttpClient jiraHttpClient) {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        return new JiraCloudServiceFactory(logger, jiraHttpClient, new Gson());
    }
}
