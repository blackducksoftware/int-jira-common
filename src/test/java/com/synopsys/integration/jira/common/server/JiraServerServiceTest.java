package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.opentest4j.TestAbortedException;

import com.synopsys.integration.jira.common.server.configuration.JiraServerRestConfig;
import com.synopsys.integration.jira.common.server.configuration.JiraServerRestConfigBuilder;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.client.IntHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Request;

public abstract class JiraServerServiceTest {
    public static final String ENV_BASE_URL = "JIRA_SERVER_URL";
    public static final String ENV_USERNAME = "JIRA_SERVER_USERNAME";
    public static final String ENV_PASSWORD = "JIRA_SERVER_PASSWORD";

    public void validateConfiguration() {
        String baseUrl = getEnvBaseUrl();
        String userEmail = getEnvUsername();
        String apiToken = getEnvPassword();

        assumeTrue(null != baseUrl, "No Jira Server base url provided");
        assumeTrue(null != userEmail, "No Jira Server user email provided");
        assumeTrue(null != apiToken, "No Jira Server API Token provided");

        try {
            IntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.ERROR);
            IntHttpClient intHttpClient = new IntHttpClient(intLogger, 60, true, ProxyInfo.NO_PROXY_INFO);

            Request basicGetRequestToJiraServer = Request.newBuilder()
                                                      .uri(baseUrl)
                                                      .method(HttpMethod.GET)
                                                      .build();
            intHttpClient.execute(basicGetRequestToJiraServer);
        } catch (Exception e) {
            throw new TestAbortedException("The Jira Server is not configured");
        }
    }

    public JiraServerRestConfig createJiraServerConfig() {
        JiraServerRestConfigBuilder builder = JiraServerRestConfig.newBuilder();

        builder
            .setUrl(getEnvBaseUrl())
            .setAuthUsername(getEnvUsername())
            .setAuthPassword(getEnvPassword());
        return builder.build();
    }

    public JiraServerServiceFactory createServiceFactory() {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraServerRestConfig serverConfig = createJiraServerConfig();
        return serverConfig.createJiraServerServiceFactory(logger);
    }

    public String getEnvBaseUrl() {
        String envBaseUrl = System.getenv(ENV_BASE_URL);
        return envBaseUrl != null ? envBaseUrl : "http://localhost:2990/jira";
    }

    public String getEnvUsername() {
        String envUsername = System.getenv(ENV_USERNAME);
        return envUsername != null ? envUsername : "admin";
    }

    public String getEnvPassword() {
        String envPassword = System.getenv(ENV_PASSWORD);
        return envPassword != null ? envPassword : "admin";
    }

}
