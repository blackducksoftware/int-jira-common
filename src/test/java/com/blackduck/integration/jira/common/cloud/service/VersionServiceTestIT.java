package com.blackduck.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.blackduck.integration.jira.common.model.components.ProjectComponent;
import com.blackduck.integration.jira.common.model.response.VersionResponseModel;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.blackduck.integration.rest.exception.IntegrationRestException;

public class VersionServiceTestIT extends JiraCloudParameterizedTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String testProjectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_TEST_PROJECT_NAME);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void findUsersByUsernameTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceTestUtility.validateConfiguration();

        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        VersionService versionService = serviceFactory.createVersionService();

        ProjectService projectService = serviceFactory.createProjectService();
        ProjectComponent projectComponent = projectService.getProjectsByName(testProjectName)
                                                .getProjects()
                                                .stream()
                                                .findFirst()
                                                .orElseThrow(() -> new IntegrationException("Expected to find project"));
        String generatedVersion = UUID.randomUUID().toString();
        VersionResponseModel version = versionService.createVersion(generatedVersion, projectComponent.getId());
        assertEquals(generatedVersion, version.getName());

        VersionResponseModel createdVersion = versionService.getVersion(version.getId());
        assertEquals(generatedVersion, createdVersion.getName());

        versionService.deleteVersion(version.getId());

        try {
            versionService.getVersion(version.getId());
            fail("Should have thrown a 404 error");
        } catch (IntegrationRestException e) {
            assertEquals(404, e.getHttpStatusCode());
        }

    }
}
