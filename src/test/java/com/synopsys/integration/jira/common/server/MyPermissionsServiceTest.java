package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.MultiPermissionResponseModel;
import com.synopsys.integration.jira.common.model.response.PermissionModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.MyPermissionsService;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

public class MyPermissionsServiceTest {
    @Test
    public void getMyPermissionsTest() throws IntegrationException {
        IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
        JiraHttpClient httpClient = JiraServerServiceTestUtility.createJiraCredentialClient(logger);
        JiraServerServiceFactory jiraServiceFactory = JiraServerServiceTestUtility.createServiceFactory(httpClient);
        
        MyPermissionsService myPermissionsService = jiraServiceFactory.createMyPermissionsService();

        String testProjectKey = JiraServerServiceTestUtility.getTestProject();
        MultiPermissionResponseModel myPermissionsResponse = myPermissionsService.getMyPermissions(testProjectKey, null, null, null);

        Map<String, PermissionModel> permissionsMap = myPermissionsResponse.extractPermissions();
        assertTrue(permissionsMap.containsKey("CREATE_ISSUE"));
        assertTrue(permissionsMap.containsKey("CLOSE_ISSUE"));
    }

}
