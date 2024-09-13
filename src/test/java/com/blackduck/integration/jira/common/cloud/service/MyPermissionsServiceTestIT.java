package com.blackduck.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.blackduck.integration.jira.common.model.response.MultiPermissionResponseModel;
import com.blackduck.integration.jira.common.model.response.PermissionModel;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;

public class MyPermissionsServiceTestIT extends JiraCloudParameterizedTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String testProjectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_TEST_PROJECT_NAME);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getMyPermissionsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceFactory jiraServiceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        MyPermissionsService myPermissionsService = jiraServiceFactory.createMyPermissionsService();

        String createIssuesPermissionName = "CREATE_ISSUES";
        String closeIssuesPermissionName = "CLOSE_ISSUES";
        List<String> permissions = Arrays.asList(createIssuesPermissionName, closeIssuesPermissionName);

        MultiPermissionResponseModel myPermissionsResponse = myPermissionsService.getMyPermissions(permissions, testProjectName, null, null, null, null, null);

        Map<String, PermissionModel> permissionsMap = myPermissionsResponse.extractPermissions();
        assertEquals(permissions.size(), permissionsMap.size());

        PermissionModel myCreateIssuesPermission = myPermissionsResponse.extractPermission(createIssuesPermissionName);
        assertTrue(myCreateIssuesPermission.getHavePermission());

        PermissionModel myCloseIssuesPermission = myPermissionsResponse.extractPermission(closeIssuesPermissionName);
        assertTrue(myCloseIssuesPermission.getHavePermission());
    }

}
