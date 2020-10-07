package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.ProjectService;

public class ProjectServiceTest extends JiraServerParameterizedTest {
    @ParameterizedTest
    @MethodSource("getParameters")
    public void getProjectsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        List<ProjectComponent> projects = projectService.getProjects().getProjects();
        assertFalse(projects.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetProject(JiraHttpClient jiraHttpClient) throws Exception {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);

        ProjectService projectService = serviceFactory.createProjectService();

        String testProject = JiraServerServiceTestUtility.getTestProject();
        List<ProjectComponent> projects = projectService.getProjectsByName(testProject).getProjects();

        assertFalse(projects.isEmpty());

        String projectKey = projects.get(0).getKey();
        assertTrue(StringUtils.isNotBlank(projectKey));

        ProjectComponent project = projectService.getProject(projectKey);
        assertTrue(testProject.equalsIgnoreCase(project.getName()));
    }

}
