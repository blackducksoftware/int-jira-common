package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.JiraHttpClient;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraCloudHttpClientTest extends JiraServiceTest {
    private static final String RESTRICTED_ENDPOINT_SPEC = "/rest/api/2/field";

    @Test
    public void authenticationTest() throws IntegrationException, IOException {
        final String baseUrl = getEnvBaseUrl();
        final String userEmail = getEnvUserEmail();
        final String apiToken = getEnvApiToken();
        validateConfiguration();

        final PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        final JiraHttpClient jiraCloudHttpClient = new JiraHttpClient(intLogger, 120, true, ProxyInfo.NO_PROXY_INFO, baseUrl, new AuthenticationSupport(), userEmail, apiToken);

        final String requestUrl = baseUrl + RESTRICTED_ENDPOINT_SPEC;
        final Request request = new Request.Builder(requestUrl).build();
        try (final Response reponse = jiraCloudHttpClient.execute(request)) {
            assertTrue(reponse.isStatusCodeOkay(), "Expected the request to be valid");
        }
    }

}
