package com.blackduck.integration.jira.common.server.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.net.ssl.SSLContext;

import org.junit.jupiter.api.Test;

import com.blackduck.integration.jira.common.rest.JiraCredentialHttpClient;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;

public class JiraServerBasicAuthRestConfigBuilderTest {

    @Test
    public void testUsingSSLContext() {
        JiraServerBasicAuthRestConfigBuilder jiraServerBasicAuthRestConfigBuilder = createValidConfigBuilder();
        assertFalse(jiraServerBasicAuthRestConfigBuilder.getSslContext().isPresent());

        JiraServerBasicAuthRestConfig jiraServerBasicAuthRestConfig = jiraServerBasicAuthRestConfigBuilder.build();
        assertFalse(jiraServerBasicAuthRestConfig.getSslContext().isPresent());

        SSLContext sslContext = assertDoesNotThrow(SSLContext::getDefault);
        jiraServerBasicAuthRestConfigBuilder.setSslContext(sslContext);
        assertTrue(jiraServerBasicAuthRestConfigBuilder.getSslContext().isPresent());
        jiraServerBasicAuthRestConfig = jiraServerBasicAuthRestConfigBuilder.build();
        assertTrue(jiraServerBasicAuthRestConfig.getSslContext().isPresent());

        PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraHttpClient jiraHttpClient = jiraServerBasicAuthRestConfig.createJiraHttpClient(intLogger);
        assertTrue(jiraHttpClient instanceof JiraCredentialHttpClient);
    }

    private JiraServerBasicAuthRestConfigBuilder createValidConfigBuilder() {
        return new JiraServerBasicAuthRestConfigBuilder()
                .setUrl("http://www.google.com/fake_but_valid_not_blank_url")
                .setAuthUsername("username")
                .setAuthPassword("password");
    }
}
