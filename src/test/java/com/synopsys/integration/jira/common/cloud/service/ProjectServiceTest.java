package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
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

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetProject(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        String testProject = JiraCloudServiceTestUtility.getTestProject();
        PageOfProjectsResponseModel projectsByName = projectService.getProjectsByName(testProject);
        List<ProjectComponent> projects = projectsByName.getProjects();

        assertFalse(projects.isEmpty());

        String projectKey = projects.get(0).getKey();
        assertTrue(StringUtils.isNotBlank(projectKey));

        ProjectComponent project = projectService.getProject(projectKey);
        assertEquals(testProject, project.getName());
    }
}
