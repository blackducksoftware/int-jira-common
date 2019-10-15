package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssuePropertyService;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.server.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.server.service.IssueService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

public class ServerIssuePropertyServiceTest extends JiraServerServiceTest {
    @Test
    public void setAndGetTest() throws IntegrationException {
        validateConfiguration();

        JiraServerServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();

        Gson gson = new Gson();

        String projectName = "Other Project";
        IssueResponseModel issue = createIssue(issueService, projectName);

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

    private IssueResponseModel createIssue(IssueService issueService, String projectName) throws IntegrationException {
        String reporter = "admin";
        String issueTypeName = "Task";

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        return issueService.createIssue(issueCreationRequestModel);
    }

    private static final class TestPropertyClass {
        private String propertyOne;
        private String propertyTwo;

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
