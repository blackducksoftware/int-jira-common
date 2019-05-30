package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class JiraServiceTest {
    public static final String ENV_BASE_URL = "JIRA_CLOUD_URL";
    public static final String ENV_USER_EMAIL = "JIRA_CLOUD_EMAIL";
    public static final String ENV_API_TOKEN = "JIRA_CLOUD_TOKEN";

    public void validateConfiguration() {
        final String baseUrl = getEnvBaseUrl();
        final String userEmail = getEnvUserEmail();
        final String apiToken = getEnvApiToken();

        assumeTrue(null != baseUrl, "No Jira Cloud base url provided");
        assumeTrue(null != userEmail, "No Jira Cloud user email provided");
        assumeTrue(null != apiToken, "No Jira Cloud API Token provided");
    }

    public String getEnvBaseUrl() {
        return System.getenv(ENV_BASE_URL);
    }

    public String getEnvUserEmail() {
        return System.getenv(ENV_USER_EMAIL);
    }

    public String getEnvApiToken() {
        return System.getenv(ENV_API_TOKEN);
    }
}
