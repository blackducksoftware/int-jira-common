package com.synopsys.integration.jira.common.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.synopsys.integration.jira.common.cloud.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.cloud.service.IssueService;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.cloud.service.ProjectService;
import com.synopsys.integration.jira.common.cloud.service.UserSearchService;
import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.IssuePropertyKeyComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.IssueCreationResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssuePropertyService;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;

public class IssuePropertyServiceTestIT extends JiraCloudParameterizedTestIT {
    private final TestProperties testProperties = new TestProperties();
    private final String userEmail = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_EMAIL);
    private final String testProjectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_TEST_PROJECT_NAME);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getPropertyKeysTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(userEmail).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(testProjectName))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));

        UUID uniqueId = UUID.randomUUID();
        String uniqueIdString = uniqueId.toString();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueIdString);
        fieldsBuilder.setSummary("Test Issue " + uniqueIdString);

        String issueType = "bug";
        List<EntityProperty> properties = new LinkedList<>();
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);
        IssueCreationResponseModel createdIssue = issueService.createIssue(requestModel);

        String testPropertyKey = "test-property-key";
        String testObjectKey = "exampleKey";
        String testJsonValue = "{\"" + testObjectKey + "\":\"value\"}";
        IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();
        issuePropertyService.setProperty(createdIssue.getKey(), testPropertyKey, testJsonValue);

        IssuePropertyKeysResponseModel propertyKeysResponse = issuePropertyService.getPropertyKeys(createdIssue.getKey());
        Optional<IssuePropertyKeyComponent> optionalFoundKey = propertyKeysResponse.getKeys().stream().findFirst();
        assertTrue(optionalFoundKey.isPresent());
        assertEquals(testPropertyKey, optionalFoundKey.map(IssuePropertyKeyComponent::getKey).get());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getPropertyTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(userEmail).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(testProjectName))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));

        UUID uniqueId = UUID.randomUUID();
        String uniqueIdString = uniqueId.toString();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueIdString);
        fieldsBuilder.setSummary("Test Issue " + uniqueIdString);

        String issueType = "bug";
        List<EntityProperty> properties = new LinkedList<>();
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);
        IssueCreationResponseModel createdIssue = issueService.createIssue(requestModel);

        String testPropertyKey = "test-property-key";
        String testObjectKey = "exampleKey";
        String testObjectValue = "exampleValue";
        String testJsonValue = "{\"" + testObjectKey + "\":\"" + testObjectValue + "\"}";
        IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();
        issuePropertyService.setProperty(createdIssue.getKey(), testPropertyKey, testJsonValue);

        IssuePropertyResponseModel propertyModel = issuePropertyService.getProperty(createdIssue.getKey(), testPropertyKey);
        assertEquals(testPropertyKey, propertyModel.getKey());

        JsonObject value = propertyModel.getValue();
        JsonPrimitive valueAsJsonPrimitive = value.getAsJsonPrimitive(testObjectKey);
        String valueFromJson = valueAsJsonPrimitive.getAsString();
        assertEquals(testObjectValue, valueFromJson);
    }

}
