package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.synopsys.integration.jira.common.model.response.MultiPermissionResponseModel;
import com.synopsys.integration.jira.common.model.response.PermissionModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;

public class MyPermissionsServiceTestIT extends JiraCloudParameterizedTestIT {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getMyPermissionsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceFactory jiraServiceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);

        MyPermissionsService myPermissionsService = jiraServiceFactory.createMyPermissionsService();

        String createIssuesPermissionName = "CREATE_ISSUES";
        String closeIssuesPermissionName = "CLOSE_ISSUES";
        List<String> permissions = Arrays.asList(createIssuesPermissionName, closeIssuesPermissionName);
        String testProjectKey = JiraCloudServiceTestUtility.getTestProject();

        MultiPermissionResponseModel myPermissionsResponse = myPermissionsService.getMyPermissions(permissions, testProjectKey, null, null, null, null, null);

        Map<String, PermissionModel> permissionsMap = myPermissionsResponse.extractPermissions();
        assertEquals(permissions.size(), permissionsMap.size());

        PermissionModel myCreateIssuesPermission = myPermissionsResponse.extractPermission(createIssuesPermissionName);
        assertTrue(myCreateIssuesPermission.getHavePermission());

        PermissionModel myCloseIssuesPermission = myPermissionsResponse.extractPermission(closeIssuesPermissionName);
        assertTrue(myCloseIssuesPermission.getHavePermission());
    }

}
