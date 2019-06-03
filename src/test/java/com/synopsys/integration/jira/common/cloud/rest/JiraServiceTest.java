package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.synopsys.integration.jira.common.cloud.configuration.JiraServerConfig;
import com.synopsys.integration.jira.common.cloud.configuration.JiraServerConfigBuilder;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

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

    public JiraServerConfig createJiraServerConfig() {

        JiraServerConfigBuilder builder = JiraServerConfig.newBuilder();

        builder.setUrl(getEnvBaseUrl())
            .setAuthUserEmail(getEnvUserEmail())
            .setApiToken(getEnvApiToken());
        return builder.build();
    }

    public JiraCloudServiceFactory createServiceFactory() {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraServerConfig serverConfig = createJiraServerConfig();
        return serverConfig.createJiraCloudServiceFactory(logger);
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
