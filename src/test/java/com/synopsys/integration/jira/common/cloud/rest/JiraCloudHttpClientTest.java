package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraCloudHttpClientTest {
    public static final String ENV_BASE_URL = "JIRA_CLOUD_URL";
    public static final String ENV_USER_EMAIL = "JIRA_CLOUD_EMAIL";
    public static final String ENV_API_TOKEN = "JIRA_CLOUD_TOKEN";

    private static final String RESTRICTED_ENDPOINT_SPEC = "/rest/api/2/field";

    @Test
    public void authenticationTest() throws IntegrationException, IOException {
        final String baseUrl = System.getenv(ENV_BASE_URL);
        final String userEmail = System.getenv(ENV_USER_EMAIL);
        final String apiToken = System.getenv(ENV_API_TOKEN);

        assumeTrue(null != baseUrl, "No Jira Cloud base url provided");
        assumeTrue(null != userEmail, "No Jira Cloud user email provided");
        assumeTrue(null != apiToken, "No Jira Cloud API Token provided");

        final PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        final JiraCloudHttpClient jiraCloudHttpClient = new JiraCloudHttpClient(intLogger, 120, true, ProxyInfo.NO_PROXY_INFO, baseUrl, new AuthenticationSupport(), userEmail, apiToken);

        final String requestUrl = baseUrl + RESTRICTED_ENDPOINT_SPEC;
        final Request request = new Request.Builder(requestUrl).build();
        try (final Response reponse = jiraCloudHttpClient.execute(request)) {
            assertTrue(reponse.isStatusCodeOkay(), "Expected the request to be valid");
        }
    }

}
