package com.synopsys.integration.jira.common.server.configuration;

import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.token.JiraAccessTokenHttpClient;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JiraServerBearerAuthRestConfigBuilderTest {

    @Test
    public void testUsingSSLContext() {
        JiraServerBearerAuthRestConfigBuilder jiraServerBearerAuthRestConfigBuilder = createValidConfigBuilder();
        assertFalse(jiraServerBearerAuthRestConfigBuilder.getSslContext().isPresent());

        JiraServerBearerAuthRestConfig jiraServerBearerAuthRestConfig = jiraServerBearerAuthRestConfigBuilder.build();
        assertFalse(jiraServerBearerAuthRestConfig.getSslContext().isPresent());

        SSLContext sslContext = assertDoesNotThrow(SSLContext::getDefault);
        jiraServerBearerAuthRestConfigBuilder.setSslContext(sslContext);
        assertTrue(jiraServerBearerAuthRestConfigBuilder.getSslContext().isPresent());
        jiraServerBearerAuthRestConfig = jiraServerBearerAuthRestConfigBuilder.build();
        assertTrue(jiraServerBearerAuthRestConfig.getSslContext().isPresent());

        PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraHttpClient jiraHttpClient = jiraServerBearerAuthRestConfig.createJiraHttpClient(intLogger);
        assertTrue(jiraHttpClient instanceof JiraAccessTokenHttpClient);
    }

    private JiraServerBearerAuthRestConfigBuilder createValidConfigBuilder() {
        return new JiraServerBearerAuthRestConfigBuilder()
                .setUrl("http://www.google.com/fake_but_valid_not_blank_url")
                .setAccessToken("access_token");
    }
}
