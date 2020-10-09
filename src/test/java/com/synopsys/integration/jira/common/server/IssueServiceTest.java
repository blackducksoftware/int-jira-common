package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.IssueFieldsComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.IssueCreationResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.server.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.server.service.IssueService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.ProjectService;

public class IssueServiceTest extends JiraServerParameterizedTest {
    @ParameterizedTest
    @MethodSource("getParameters")
    public void createIssueTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        String reporter = "admin";
        String issueTypeName = "Task";
        String projectName = JiraServerServiceTestUtility.getTestProject();

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        IssueCreationResponseModel issue = issueService.createIssue(issueCreationRequestModel);
        assertNotNull(issue, "Expected an issue to be created.");
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getIssueFieldsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        IssueTypeService issueTypeService = serviceFactory.createIssueTypeService();
        ProjectService projectService = serviceFactory.createProjectService();

        String testProject = JiraServerServiceTestUtility.getTestProject();
        String projectKey = projectService.getProjectsByName(testProject)
                                .stream()
                                .findFirst()
                                .map(ProjectComponent::getKey)
                                .orElseThrow(() -> new IntegrationException("Expected to find project"));

        String issueId = issueTypeService.getAllIssueTypes()
                             .stream()
                             .filter(issueType -> "Task".equalsIgnoreCase(issueType.getName()))
                             .map(IssueTypeResponseModel::getId)
                             .findFirst()
                             .orElseThrow(() -> new IntegrationException("Expected to find issue type task"));

        JiraResponse issueFields = issueService.getIssueFields(projectKey, issueId);
        assertTrue(StringUtils.isNotBlank(issueFields.getContent()));
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void createIssueWithCustomFieldTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        String reporter = "admin";
        String issueTypeName = "Task";
        String projectName = JiraServerServiceTestUtility.getTestProject();

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Custom field Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");
        issueRequestModelFieldsBuilder.setPriority("3");

        String key = "customfield_10107";
        String value = "Custom field using rest";
        issueRequestModelFieldsBuilder.setField(key, value);

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        IssueCreationResponseModel issueCreationResponse = issueService.createIssue(issueCreationRequestModel);

        assertNotNull(issueCreationResponse);
        assertTrue(StringUtils.isNotBlank(issueCreationResponse.getKey()));

        IssueResponseModel issue = issueService.getIssue(issueCreationResponse.getKey());

        IssueFieldsComponent fields = issue.getFields();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(fields.getJson(), JsonObject.class);
        JsonPrimitive customValueFromResponse = jsonObject.getAsJsonPrimitive(key);

        assertEquals(value, customValueFromResponse.getAsString());
    }

}
