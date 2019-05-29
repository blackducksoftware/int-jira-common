package com.synopsys.integration.jira.common.cloud.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class JiraServerConfigBuilderTest {
    @Test
    public void testSimpleValidConfig() {
        JiraServerConfigBuilder jiraServerConfigBuilder = createBuilderWithoutAccessToken();
        jiraServerConfigBuilder.setApiToken("fake but valid (not blank) api token");

        JiraServerConfig jiraServerConfig = jiraServerConfigBuilder.build();
        assertNotNull(jiraServerConfig);
    }

    @Test
    public void testPopulatedFromEnvironment() {
        JiraServerConfigBuilder jiraServerConfigBuilder = JiraServerConfig.newBuilder();

        Map<String, String> fakeEnvironment = new HashMap<>();
        fakeEnvironment.put("jira.auth.user.email", "fake but valid (not blank) user email");
        fakeEnvironment.put("jira.api.token", "fake but valid (not blank) access token");
        fakeEnvironment.put("jira.url", "http://www.google.com/fake_but_valid_not_blank_url");
        fakeEnvironment.put("jira.timeout.in.seconds", "120");
        jiraServerConfigBuilder.setProperties(fakeEnvironment.entrySet());

        assertTrue(jiraServerConfigBuilder.isValid());
        assertEquals("fake but valid (not blank) user email", jiraServerConfigBuilder.getAuthUserEmail());
        assertEquals("fake but valid (not blank) access token", jiraServerConfigBuilder.getApiToken());
        assertEquals("http://www.google.com/fake_but_valid_not_blank_url", jiraServerConfigBuilder.getUrl());
        assertEquals(120, jiraServerConfigBuilder.getTimeoutInSeconds());
    }

    @Test
    public void testInvalidTimeoutFromEnvironment() {
        JiraServerConfigBuilder jiraServerConfigBuilder = JiraServerConfig.newBuilder();

        Map<String, String> fakeEnvironment = new HashMap<>();
        fakeEnvironment.put("jira.timeout.in.seconds", "invalid - not numeric");
        jiraServerConfigBuilder.setProperties(fakeEnvironment.entrySet());

        assertEquals(JiraServerConfigBuilder.DEFAULT_TIMEOUT_SECONDS, jiraServerConfigBuilder.getTimeoutInSeconds());
    }

    @Test
    public void testUrlConfigFromEnvironment() {
        JiraServerConfigBuilder jiraServerConfigBuilder = JiraServerConfig.newBuilder();
        jiraServerConfigBuilder.setAuthUserEmail("fake but valid (not blank) user email");
        jiraServerConfigBuilder.setApiToken("fake but valid (not blank) access token");
        jiraServerConfigBuilder.setTimeoutInSeconds(120);

        assertFalse(jiraServerConfigBuilder.isValid());

        Map<String, String> fakeEnvironment = new HashMap<>();
        jiraServerConfigBuilder.setProperties(fakeEnvironment.entrySet());
        assertFalse(jiraServerConfigBuilder.isValid());

        fakeEnvironment.put("JIRA_URL", "http://www.google.com/fake_but_valid_not_blank_url");
        jiraServerConfigBuilder.setProperties(fakeEnvironment.entrySet());
        assertTrue(jiraServerConfigBuilder.isValid());

        jiraServerConfigBuilder = JiraServerConfig.newBuilder();
        jiraServerConfigBuilder.setAuthUserEmail("fake but valid (not blank) user email");
        jiraServerConfigBuilder.setApiToken("fake but valid (not blank) access token");
        jiraServerConfigBuilder.setTimeoutInSeconds(120);

        fakeEnvironment = new HashMap<>();
        fakeEnvironment.put("jira.url", "http://www.google.com/fake_but_valid_not_blank_url");
        jiraServerConfigBuilder.setProperties(fakeEnvironment.entrySet());
        assertTrue(jiraServerConfigBuilder.isValid());

        jiraServerConfigBuilder = JiraServerConfig.newBuilder();
        jiraServerConfigBuilder.setAuthUserEmail("fake but valid (not blank) user email");
        jiraServerConfigBuilder.setApiToken("fake but valid (not blank) access token");
        jiraServerConfigBuilder.setTimeoutInSeconds(120);
    }

    @Test
    public void testResolveAccessTokenFromEnvVar() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jira.api.token", "fake but valid not blank access token");

        JiraServerConfigBuilder jiraServerConfigBuilder = createBuilderWithoutAccessToken();
        assertFalse(jiraServerConfigBuilder.isValid());

        jiraServerConfigBuilder.setProperties(properties.entrySet());
        assertTrue(jiraServerConfigBuilder.isValid());
    }

    @Test
    public void testCommonJiraConfig() {
        Map<String, String> commonUserEnvironment = new HashMap<>();
        commonUserEnvironment.put("JIRA_URL", "http://www.google.com");
        commonUserEnvironment.put("JIRA_AUTH_USER_EMAIL", "fake but valid not blank user email");
        commonUserEnvironment.put("JIRA_API_TOKEN", "fake but valid not blank access token");
        commonUserEnvironment.put("BLACKDUCK_URL", "http://www.blackducksoftware.com");
        commonUserEnvironment.put("BLACKDUCK_USERNAME", "username");
        commonUserEnvironment.put("BLACKDUCK_PASSWORD", "password");

        JiraServerConfigBuilder jiraServerConfigBuilder = new JiraServerConfigBuilder();
        Set<String> keys = jiraServerConfigBuilder.getEnvironmentVariableKeys();
        for (String key : keys) {
            if (commonUserEnvironment.containsKey(key)) {
                jiraServerConfigBuilder.setProperty(key, commonUserEnvironment.get(key));
            }
        }

        JiraServerConfig jiraServerConfig = jiraServerConfigBuilder.build();
        assertEquals("http://www.google.com", jiraServerConfig.getJiraUrl().toString());
        assertEquals("fake but valid not blank user email", jiraServerConfig.getAuthUserEmail());
        assertEquals("fake but valid not blank access token", jiraServerConfig.getApiToken());
    }

    private JiraServerConfigBuilder createBuilderWithoutAccessToken() {
        JiraServerConfigBuilder jiraServerConfigBuilder = JiraServerConfig.newBuilder();

        jiraServerConfigBuilder.setUrl("http://www.google.com/fake_but_valid_not_blank_url");
        jiraServerConfigBuilder.setAuthUserEmail("fake but valid (not blank) user email");
        jiraServerConfigBuilder.setTimeoutInSeconds(120);

        return jiraServerConfigBuilder;
    }
}
