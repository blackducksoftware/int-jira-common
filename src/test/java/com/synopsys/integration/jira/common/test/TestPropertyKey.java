package com.synopsys.integration.jira.common.test;

public enum TestPropertyKey {
    TEST_JIRA_SERVER_URL("jira.server.url"),
    TEST_JIRA_SERVER_USERNAME("jira.server.username"),
    TEST_JIRA_SERVER_PASSWORD("jira.server.password"),
    TEST_JIRA_SERVER_TEST_PROJECT_NAME("jira.server.test.project.name"),
    TEST_JIRA_SERVER_ISSUE_TYPE("jira.server.issue.type"),
    TEST_JIRA_SERVER_RESOLVE_TRANSITION("jira.server.resolve.transition"),
    TEST_JIRA_SERVER_REOPEN_TRANSITION("jira.server.reopen.transition"),
    TEST_JIRA_SERVER_OAUTH_ACCESS_TOKEN("jira.server.oauth.access.token"),

    TEST_JIRA_CLOUD_URL("jira.cloud.url"),
    TEST_JIRA_CLOUD_EMAIL("jira.cloud.email"),
    TEST_JIRA_CLOUD_API_TOKEN("jira.cloud.api.token"),
    TEST_JIRA_CLOUD_TEST_PROJECT_NAME("jira.cloud.test.project.name"),
    TEST_JIRA_CLOUD_ISSUE_TYPE("jira.cloud.issue.type"),
    TEST_JIRA_CLOUD_RESOLVE_TRANSITION("jira.cloud.resolve.transition"),
    TEST_JIRA_CLOUD_REOPEN_TRANSITION("jira.cloud.reopen.transition"),
    TEST_JIRA_CLOUD_TEST_PROPERTY_KEY("jira.cloud.test.property.key"),
    TEST_JIRA_CLOUD_OAUTH_ACCESS_TOKEN("jira.cloud.oauth.access.token"),

    TEST_JIRA_OAUTH_CONSUMER_KEY("jira.oauth.consumer.key"),
    TEST_JIRA_OAUTH_PRIVATE_KEY("jira.oauth.private.key"),

    TEST_JIRA_OAUTH_TEMP_TOKEN("jira.oauth.temp.token"),
    TEST_JIRA_OAUTH_VERIFICATION_CODE("jira.oauth.verification.code"),

    TEST_JIRA_CUSTOM_FIELD_TEST_ID("jira.custom.field.test.id");

    private final String propertyKey;

    TestPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
