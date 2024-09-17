package com.blackduck.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.response.IssueCreationResponseModel;
import com.blackduck.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.blackduck.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.service.IssuePropertyService;
import com.blackduck.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.blackduck.integration.jira.common.server.model.IssueCreationRequestModel;
import com.blackduck.integration.jira.common.server.service.IssueService;
import com.blackduck.integration.jira.common.server.service.JiraServerServiceFactory;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ServerIssuePropertyServiceTestIT extends JiraServerParameterizedTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String projectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);
    private final String reporter = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
    private final String issueTypeName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_ISSUE_TYPE);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void setAndGetTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();

        Gson gson = new Gson();

        IssueCreationResponseModel issue = createIssue(issueService, projectName);

        String propertyKey = "examplePropertyKey";
        try {
            IssuePropertyKeysResponseModel propertyKeys = issuePropertyService.getPropertyKeys(issue.getKey());
            assertTrue(propertyKeys.getKeys().isEmpty(), "Expected no property keys to be set");

            TestPropertyClass testPropertyClass = new TestPropertyClass("test value 1", "test value 2");
            String testPropertyClassString = gson.toJson(testPropertyClass);
            issuePropertyService.setProperty(issue.getKey(), propertyKey, testPropertyClassString);

            IssuePropertyKeysResponseModel propertyKeysPostPut = issuePropertyService.getPropertyKeys(issue.getKey());
            assertEquals(1, propertyKeysPostPut.getKeys().size());

            IssuePropertyResponseModel property = issuePropertyService.getProperty(issue.getKey(), propertyKey);
            assertEquals(propertyKey, property.getKey());

            JsonObject propertyValue = property.getValue();
            TestPropertyClass deserializedTestPropertiesClass = gson.fromJson(propertyValue, TestPropertyClass.class);
            assertEquals(testPropertyClass.getPropertyOne(), deserializedTestPropertiesClass.getPropertyOne());
            assertEquals(testPropertyClass.getPropertyTwo(), deserializedTestPropertiesClass.getPropertyTwo());
        } finally {
            issueService.deleteIssue(issue.getId());
        }
    }

    private IssueCreationResponseModel createIssue(IssueService issueService, String projectName) throws IntegrationException {
        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        return issueService.createIssue(issueCreationRequestModel);
    }

    private static final class TestPropertyClass {
        private final String propertyOne;
        private final String propertyTwo;

        public TestPropertyClass(String propertyOne, String propertyTwo) {
            this.propertyOne = propertyOne;
            this.propertyTwo = propertyTwo;
        }

        public String getPropertyOne() {
            return propertyOne;
        }

        public String getPropertyTwo() {
            return propertyTwo;
        }

    }

}
