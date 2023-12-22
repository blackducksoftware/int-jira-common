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
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;

class IssueServiceTestIT extends JiraServerParameterizedTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String projectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);
    private final String reporter = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
    private final String issueTypeName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_ISSUE_TYPE);

    @ParameterizedTest
    @MethodSource("getParameters")
    void createIssueTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        IssueCreationResponseModel issue = issueService.createIssue(issueCreationRequestModel);
        assertNotNull(issue, "Expected an issue to be created.");
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    void createIssueWithoutReporterTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(null, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        IssueCreationResponseModel issue = issueService.createIssue(issueCreationRequestModel);
        assertNotNull(issue, "Expected an issue to be created.");
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    void createIssueWithCustomFieldTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Custom field Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");
        issueRequestModelFieldsBuilder.setPriority("3");

        String key = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CUSTOM_FIELD_TEST_ID);
        String value = "Custom field using rest";
        issueRequestModelFieldsBuilder.setField(key, value);

        FieldService fieldService = serviceFactory.createFieldService();
        List<CustomFieldCreationResponseModel> jiraFieldModels = fieldService.getUserVisibleFields();
        Optional<CustomFieldCreationResponseModel> model = jiraFieldModels.stream()
                                                               .filter(field -> key.equals(field.getId()))
                                                               .findAny();
        assertTrue(model.isPresent(), "Cannot find a custom field with key: " + key
                                          + ". Check if the custom field is present on the server. If this check fails, a new custom field must be created on the server and the TEST_JIRA_CUSTOM_FIELD_TEST_ID property updated. ");

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
