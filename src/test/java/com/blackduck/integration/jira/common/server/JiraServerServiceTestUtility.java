package com.blackduck.integration.jira.common.server;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.apache.commons.lang3.StringUtils;
import org.opentest4j.TestAbortedException;

import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.server.configuration.JiraServerBasicAuthRestConfigBuilder;
import com.blackduck.integration.jira.common.server.configuration.JiraServerBearerAuthRestConfigBuilder;
import com.blackduck.integration.jira.common.server.configuration.JiraServerRestConfig;
import com.blackduck.integration.jira.common.server.service.JiraServerServiceFactory;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;
import com.blackduck.integration.rest.HttpMethod;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.client.IntHttpClient;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.request.Request;
import com.google.gson.Gson;

public final class JiraServerServiceTestUtility {
    private static final Gson gson = new Gson();

    private static final TestProperties testProperties = TestProperties.loadTestProperties();

    public static void validateConfiguration() {
        String baseUrl = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL);
        String username = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
        String password = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PASSWORD);
        String testProject = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);
        String personalAccessToken = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PERSONAL_ACCESS_TOKEN);

        assumeTrue(StringUtils.isNotBlank(baseUrl), "No Jira Server base url provided");
        assumeTrue(StringUtils.isNotBlank(username), "No Jira Server username provided");
        assumeTrue(StringUtils.isNotBlank(password), "No Jira Server password provided");
        assumeTrue(StringUtils.isNotBlank(testProject), "No Jira Server test project provided");
        assumeTrue(StringUtils.isNotBlank(personalAccessToken), "No Jira Server personal access token provided");

        try {
            IntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.ERROR);
            IntHttpClient intHttpClient = new IntHttpClient(intLogger, gson, 60, true, ProxyInfo.NO_PROXY_INFO);

            Request basicGetRequestToJiraServer = new Request.Builder()
                .url(new HttpUrl(baseUrl))
                .method(HttpMethod.GET)
                .build();
            intHttpClient.execute(basicGetRequestToJiraServer);
        } catch (Exception e) {
            throw new TestAbortedException("The Jira Server is not configured");
        }
    }

    public static JiraServerRestConfig createBasicAuthJiraServerConfig() {
        JiraServerBasicAuthRestConfigBuilder builder = new JiraServerBasicAuthRestConfigBuilder();

        builder
            .setUrl(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL))
            .setAuthUsername(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME))
            .setAuthPassword(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PASSWORD));

        return builder.build();
    }

    public static JiraServerRestConfig createBearerAuthJiraServerConfig() {
        JiraServerBearerAuthRestConfigBuilder builder = new JiraServerBearerAuthRestConfigBuilder();

        builder
            .setUrl(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL))
            .setAccessToken(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PERSONAL_ACCESS_TOKEN));

        return builder.build();
    }

    public static JiraHttpClient createJiraCredentialClient(IntLogger logger) {
        return createBasicAuthJiraServerConfig().createJiraHttpClient(logger);
    }

    public static JiraHttpClient createJiraBearerAuthClient(IntLogger logger) {
        return createBearerAuthJiraServerConfig().createJiraHttpClient(logger);
    }

    public static JiraServerServiceFactory createServiceFactory(JiraHttpClient jiraHttpClient) {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        return new JiraServerServiceFactory(logger, jiraHttpClient, new Gson());
    }
}
