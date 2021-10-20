package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.VersionResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;

public class ProjectServiceTestIT extends JiraCloudParameterizedTestIT {
    private final TestProperties testProperties = new TestProperties();
    private final String testProjectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_TEST_PROJECT_NAME);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetPaginatedProjects(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        int limit = 5;
        int startAt = 0;
        PageOfProjectsResponseModel pagedModel = projectService.getProjects(limit, startAt);
        assertNotNull(pagedModel);
        assertNotNull(pagedModel.getProjects());
        assertEquals(limit, pagedModel.getMaxResults());
        assertEquals(startAt, pagedModel.getStartAt());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetManyPaginatedProjects(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        int limit = 1000;
        PageOfProjectsResponseModel pagedModel = projectService.getProjects(limit, 0);
        assertNotNull(pagedModel);
        assertNotNull(pagedModel.getProjects());
        assertFalse(pagedModel.getProjects().isEmpty());
        assertTrue(limit > pagedModel.getMaxResults());
    }

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

        PageOfProjectsResponseModel projectsByName = projectService.getProjectsByName(testProjectName);
        List<ProjectComponent> projects = projectsByName.getProjects();

        assertFalse(projects.isEmpty());

        String projectKey = projects.get(0).getKey();
        assertTrue(StringUtils.isNotBlank(projectKey));

        ProjectComponent project = projectService.getProject(projectKey);
        assertEquals(testProjectName, project.getName());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetProjectVersions(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjectsByName(testProjectName);

        assertTrue(projects.getTotal() > 0);

        String projectKey = projects.getProjects().get(0).getKey();
        assertTrue(StringUtils.isNotBlank(projectKey));

        List<VersionResponseModel> project = projectService.getProjectVersions(projectKey);
        assertTrue(project.size() > 0);
    }
}
