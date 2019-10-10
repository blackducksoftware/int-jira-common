package com.synopsys.integration.jira.common.cloud.rest;

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
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.rest.service.IssuePropertyService;
import com.synopsys.integration.jira.common.cloud.rest.service.IssueService;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.rest.service.ProjectService;
import com.synopsys.integration.jira.common.cloud.rest.service.UserSearchService;
import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.IssuePropertyKeyComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.request.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;

public class IssuePropertyServiceTest extends JiraServiceTest {
    @Test
    public void getPropertyKeysTest() throws IntegrationException {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();
        final ProjectService projectService = serviceFactory.createProjectService();

        final PageOfProjectsResponseModel projects = projectService.getProjects();
        final UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                         .findFirst()
                                                         .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        final ProjectComponent project = projects.getProjects().stream()
                                             .findFirst()
                                             .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));

        final UUID uniqueId = UUID.randomUUID();
        final String uniqueIdString = uniqueId.toString();

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueIdString);
        fieldsBuilder.setSummary("Test Issue " + uniqueIdString);

        final String issueType = "bug";
        final List<EntityProperty> properties = new LinkedList<>();
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);
        final IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        final String testPropertyKey = "test-property-key";
        final String testObjectKey = "exampleKey";
        final String testJsonValue = "{\"" + testObjectKey + "\":\"value\"}";
        final IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();
        issuePropertyService.setProperty(createdIssue.getKey(), testPropertyKey, testJsonValue);

        final IssuePropertyKeysResponseModel propertyKeysResponse = issuePropertyService.getPropertyKeys(createdIssue.getKey());
        final Optional<IssuePropertyKeyComponent> optionalFoundKey = propertyKeysResponse.getKeys().stream().findFirst();
        assertTrue(optionalFoundKey.isPresent());
        assertEquals(testPropertyKey, optionalFoundKey.map(IssuePropertyKeyComponent::getKey).get());
    }

    @Test
    public void getPropertyTest() throws IntegrationException {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();
        final ProjectService projectService = serviceFactory.createProjectService();

        final PageOfProjectsResponseModel projects = projectService.getProjects();
        final UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                         .findFirst()
                                                         .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        final ProjectComponent project = projects.getProjects().stream()
                                             .findFirst()
                                             .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));

        final UUID uniqueId = UUID.randomUUID();
        final String uniqueIdString = uniqueId.toString();

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueIdString);
        fieldsBuilder.setSummary("Test Issue " + uniqueIdString);

        final String issueType = "bug";
        final List<EntityProperty> properties = new LinkedList<>();
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);
        final IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        final String testPropertyKey = "test-property-key";
        final String testObjectKey = "exampleKey";
        final String testObjectValue = "exampleValue";
        final String testJsonValue = "{\"" + testObjectKey + "\":\"" + testObjectValue + "\"}";
        final IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();
        issuePropertyService.setProperty(createdIssue.getKey(), testPropertyKey, testJsonValue);

        final IssuePropertyResponseModel propertyModel = issuePropertyService.getProperty(createdIssue.getKey(), testPropertyKey);
        assertEquals(testPropertyKey, propertyModel.getKey());

        final JsonObject value = propertyModel.getValue();
        final JsonPrimitive valueAsJsonPrimitive = value.getAsJsonPrimitive(testObjectKey);
        final String valueFromJson = valueAsJsonPrimitive.getAsString();
        assertEquals(testObjectValue, valueFromJson);
    }

}
