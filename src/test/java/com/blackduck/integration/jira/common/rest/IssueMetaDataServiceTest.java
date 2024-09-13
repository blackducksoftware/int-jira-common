package com.blackduck.integration.jira.common.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.response.IssueCreateMetadataResponseModel;
import com.blackduck.integration.jira.common.model.response.IssueTypeResponseModel;
import com.blackduck.integration.jira.common.model.response.ProjectIssueCreateMetadataResponseModel;
import com.blackduck.integration.jira.common.rest.service.IssueMetaDataService;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;

public class IssueMetaDataServiceTest {

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
