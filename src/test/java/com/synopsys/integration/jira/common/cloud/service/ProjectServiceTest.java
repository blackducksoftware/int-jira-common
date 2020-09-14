package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.jira.common.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;

public class ProjectServiceTest extends JiraCloudParameterizedTest {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetAllProjects(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel pagedModel = projectService.getProjects();
        assertNotNull(pagedModel);
        assertNotNull(pagedModel.getProjects());
        assertFalse(pagedModel.getProjects().isEmpty());
    }
}
