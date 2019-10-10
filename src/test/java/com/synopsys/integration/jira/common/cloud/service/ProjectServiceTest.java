package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;

public class ProjectServiceTest extends JiraServiceTest {

    @Test
    public void testGetAllProjects() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();

        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel pagedModel = projectService.getProjects();
        assertNotNull(pagedModel);
        assertNotNull(pagedModel.getProjects());
        assertFalse(pagedModel.getProjects().isEmpty());
    }
}
