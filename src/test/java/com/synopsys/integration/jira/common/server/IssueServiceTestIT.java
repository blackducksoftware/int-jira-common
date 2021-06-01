package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.IssueFieldsComponent;
import com.synopsys.integration.jira.common.model.response.CustomFieldCreationResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueCreationResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.server.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.server.service.FieldService;
import com.synopsys.integration.jira.common.server.service.IssueService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

public class IssueServiceTestIT extends JiraServerParameterizedTestIT {
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
    public void createIssueWithoutReporterTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        String reporter = null;
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

        String key = "customfield_10700";
        String value = "Custom field using rest";
        issueRequestModelFieldsBuilder.setField(key, value);

        FieldService fieldService = serviceFactory.createFieldService();
        List<CustomFieldCreationResponseModel> jiraFieldModels = fieldService.getUserVisibleFields();

        Optional<CustomFieldCreationResponseModel> model = jiraFieldModels.stream()
                                                               .filter(field -> key.equals(field.getId()))
                                                               .findAny();
        // Check if the custom field is present on the server. If this check fails, a new custom field must be created and
        // the variable "key" must be updated.
        assertTrue(model.isPresent());

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
