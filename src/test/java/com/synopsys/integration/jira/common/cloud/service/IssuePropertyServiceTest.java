package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.IssuePropertyKeyComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;

public class IssuePropertyServiceTest extends JiraCloudServiceTest {
    @Test
    public void getPropertyKeysTest() throws IntegrationException {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
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
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);

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

    @Test
    public void getPropertyTest() throws IntegrationException {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
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
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);

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
