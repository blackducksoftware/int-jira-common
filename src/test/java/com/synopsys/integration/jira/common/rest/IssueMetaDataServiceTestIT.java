package com.synopsys.integration.jira.common.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.cloud.service.ProjectService;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.IssueCreateMetadataResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueCreatemetaFieldResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.ProjectIssueCreateMetadataResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssueMetaDataService;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;

public class IssueMetaDataServiceTestIT extends JiraCloudParameterizedTestIT {
    @ParameterizedTest
    @MethodSource("getParameters")
    public void testIssueFields(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueTypeService issueTypeService = serviceFactory.createIssueTypeService();
        ProjectService projectService = serviceFactory.createProjectService();
        IssueMetaDataService issueMetadataService = serviceFactory.createIssueMetadataService();

        String testProject = JiraCloudServiceTestUtility.getTestProject();
        String projectKey = projectService.getProjectsByName(testProject)
                                .getProjects()
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

        IssueCreateMetadataResponseModel issueFields = issueMetadataService.getCreateMetadataProjectIssueTypeFields(projectKey, issueId);
        assertTrue(StringUtils.isNotBlank(issueFields.getExpand()));
        assertNotNull(issueFields.getProjects());
        assertTrue(issueFields.getProjects().size() > 0);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testIssueFieldsExtraction(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueTypeService issueTypeService = serviceFactory.createIssueTypeService();
        ProjectService projectService = serviceFactory.createProjectService();
        IssueMetaDataService issueMetadataService = serviceFactory.createIssueMetadataService();

        String testProject = JiraCloudServiceTestUtility.getTestProject();
        String projectKey = projectService.getProjectsByName(testProject)
                                .getProjects()
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

        IssueCreateMetadataResponseModel issueFields = issueMetadataService.getCreateMetadataProjectIssueTypeFields(projectKey, issueId);
        assertTrue(StringUtils.isNotBlank(issueFields.getExpand()));
        assertNotNull(issueFields.getProjects());

        List<ProjectIssueCreateMetadataResponseModel> projects = issueFields.getProjects();
        assertTrue(projects.size() > 0);

        ProjectIssueCreateMetadataResponseModel projectIssueCreateMetadataResponseModel = projects.get(0);
        IssueTypeResponseModel issueTypeResponseModel = projectIssueCreateMetadataResponseModel.getIssueTypes().get(0);
        Map<String, IssueCreatemetaFieldResponseModel> fields = issueTypeResponseModel.getTypedFields(new Gson());

        assertNotNull(fields);
        assertTrue(fields.entrySet().size() > 0);
        assertTrue(fields.keySet().contains("summary"));
    }
}
