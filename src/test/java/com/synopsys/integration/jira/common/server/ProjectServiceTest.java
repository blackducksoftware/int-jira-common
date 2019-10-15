package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.ProjectService;

public class ProjectServiceTest extends JiraServerServiceTest {
    @Test
    public void getProjectsTest() throws IntegrationException {
        validateConfiguration();
        JiraServerServiceFactory serviceFactory = createServiceFactory();

        ProjectService projectService = serviceFactory.createProjectService();

        List<ProjectComponent> projects = projectService.getProjects();
        assertFalse(projects.isEmpty());
    }

}
