package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.apache.commons.lang3.StringUtils;
import org.opentest4j.TestAbortedException;

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.configuration.JiraServerRestConfig;
import com.synopsys.integration.jira.common.server.configuration.JiraServerRestConfigBuilder;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.client.IntHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Request;

public final class JiraServerServiceTestUtility {
    private static final Gson gson = new Gson();

    private static final TestProperties testProperties = new TestProperties();

    public static void validateConfiguration() {
        /* TODO
        String baseUrl = getEnvBaseUrl();
        String userEmail = getEnvUsername();
        String apiToken = getEnvPassword();
        String testProject = getTestProject();
         */

        //TODO: some of these names don't make much sense, userEmail and apiToken should be possibly renamed to username/password
        String baseUrl = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL);
        String userEmail = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
        String apiToken = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PASSWORD);
        String testProject = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);

        assumeTrue(StringUtils.isNotBlank(baseUrl), "No Jira Server base url provided");
        assumeTrue(StringUtils.isNotBlank(userEmail), "No Jira Server user email provided");
        assumeTrue(StringUtils.isNotBlank(apiToken), "No Jira Server API Token provided");
        assumeTrue(StringUtils.isNotBlank(testProject), "No Jira Server test project provided");

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

    public static JiraServerRestConfig createJiraServerConfig() {
        JiraServerRestConfigBuilder builder = JiraServerRestConfig.newBuilder();

        /* TODO
        builder
            .setUrl(getEnvBaseUrl())
            .setAuthUsername(getEnvUsername())
            .setAuthPassword(getEnvPassword());
         */
        builder
            .setUrl(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL))
            .setAuthUsername(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME))
            .setAuthPassword(testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PASSWORD));

        return builder.build();
    }

    public static JiraHttpClient createJiraCredentialClient(IntLogger logger) {
        return createJiraServerConfig().createJiraHttpClient(logger);
    }

    public static JiraServerServiceFactory createServiceFactory(JiraHttpClient jiraHttpClient) {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        return new JiraServerServiceFactory(logger, jiraHttpClient, new Gson());
    }

    /*
    public static String getEnvBaseUrl() {
        String envBaseUrl = System.getenv(JiraTestEnvVars.SERVER_BASE_URL);
        return envBaseUrl != null ? envBaseUrl : "http://localhost:2990/jira";
    }

    public static String getEnvUsername() {
        //String envUsername = System.getenv(JiraTestEnvVars.SERVER_USERNAME);
        //return envUsername != null ? envUsername : "admin";

        String envUsername = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
        return envUsername;
    }

    public static String getEnvPassword() {
        String envPassword = System.getenv(JiraTestEnvVars.SERVER_PASSWORD);
        return envPassword != null ? envPassword : "admin";
    }

    public static String getTestProject() {
        String envTestProject = System.getenv(JiraTestEnvVars.SERVER_TEST_PROJECT);
        return envTestProject != null ? envTestProject : "Test Project";
    }

    public static String getTestCustomFieldId() {
        String envTestCustomField = System.getenv(JiraTestEnvVars.TEST_CUSTOM_FIELD_ID);
        assumeTrue(envTestCustomField != null);
        return envTestCustomField;
    }

    public static String getOAuthAccessToken() {
        return System.getenv(JiraTestEnvVars.SERVER_OAUTH_ACCESS_TOKEN);
    }
     */

}
