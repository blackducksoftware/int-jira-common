package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.cloud.configuration.JiraServerConfig;
import com.synopsys.integration.jira.common.cloud.configuration.JiraServerConfigBuilder;
import com.synopsys.integration.jira.common.cloud.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.rest.service.ProjectService;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

public class ProjectServiceTest extends JiraServiceTest {

    @Test
    public void testGetAllProjects() throws Exception {
        validateConfiguration();
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraServerConfigBuilder builder = JiraServerConfig.newBuilder();

        builder.setUrl(getEnvBaseUrl())
            .setAuthUserEmail(getEnvUserEmail())
            .setApiToken(getEnvApiToken());
        JiraServerConfig serverConfig = builder.build();
        JiraCloudServiceFactory serviceFactory = serverConfig.createJiraCloudServiceFactory(logger);

        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel pagedModel = projectService.getProjects();
        assertNotNull(pagedModel);
        assertNotNull(pagedModel.getProjects());
        assertFalse(pagedModel.getProjects().isEmpty());
    }
}
