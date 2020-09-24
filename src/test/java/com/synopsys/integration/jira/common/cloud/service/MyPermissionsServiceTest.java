package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.MultiPermissionResponseModel;
import com.synopsys.integration.jira.common.model.response.PermissionModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.MyPermissionsService;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

public class MyPermissionsServiceTest {
    @Test
    public void getMyPermissionsTest() throws IntegrationException {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraHttpClient httpClient = JiraCloudServiceTestUtility.createJiraCredentialClient(logger);
        JiraCloudServiceFactory jiraServiceFactory = JiraCloudServiceTestUtility.createServiceFactory(httpClient);

        MyPermissionsService myPermissionsService = new MyPermissionsService(jiraServiceFactory.getJiraApiClient());

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
