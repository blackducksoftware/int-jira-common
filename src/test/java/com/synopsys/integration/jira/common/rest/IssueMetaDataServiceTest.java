package com.synopsys.integration.jira.common.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.cloud.service.ProjectService;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.IssueCreateMetadataResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.ProjectIssueCreateMetadataResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssueMetaDataService;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;

public class IssueMetaDataServiceTest extends JiraCloudParameterizedTest {

    @Test
    public void issueTypeInProjectTest() throws IntegrationException {
        JiraApiClient jiraApiClient = Mockito.mock(JiraApiClient.class);
        IssueCreateMetadataResponseModel response = createResponse();
        Mockito.when(jiraApiClient.get(Mockito.any(), Mockito.eq(IssueCreateMetadataResponseModel.class))).thenReturn(response);
        Mockito.when(jiraApiClient.getBaseUrl()).thenReturn("https://host");
        IssueMetaDataService service = new IssueMetaDataService(jiraApiClient);

        assertTrue(service.doesProjectContainIssueType("TEST", "VALID_TYPE_1"));
        assertTrue(service.doesProjectContainIssueType("TEST", "VALID_TYPE_2"));
        assertFalse(service.doesProjectContainIssueType("TEST", "INVALID_TYPE"));
    }

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

    private IssueCreateMetadataResponseModel createResponse() {
        IssueTypeResponseModel issueTypeResponseModel = Mockito.mock(IssueTypeResponseModel.class);
        Mockito.when(issueTypeResponseModel.getName()).thenReturn("VALID_TYPE_1");
        List<IssueTypeResponseModel> issueTypes = new ArrayList<>();
        issueTypes.add(issueTypeResponseModel);
        issueTypeResponseModel = Mockito.mock(IssueTypeResponseModel.class);
        Mockito.when(issueTypeResponseModel.getName()).thenReturn("VALID_TYPE_2");
        issueTypes.add(issueTypeResponseModel);

        ProjectIssueCreateMetadataResponseModel project = new ProjectIssueCreateMetadataResponseModel("expand", "self", "1", "TEST", "TEST", issueTypes);

        List<ProjectIssueCreateMetadataResponseModel> projects = new ArrayList<>();
        projects.add(project);
        IssueCreateMetadataResponseModel response = new IssueCreateMetadataResponseModel("projects", projects);
        return response;
    }
}
