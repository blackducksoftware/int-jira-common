package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.MultiPermissionResponseModel;
import com.synopsys.integration.jira.common.model.response.PermissionModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.MyPermissionsService;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;

public class MyPermissionsServiceTestIT extends JiraServerParameterizedTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String projectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getMyPermissionsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceFactory jiraServiceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);

        MyPermissionsService myPermissionsService = jiraServiceFactory.createMyPermissionsService();

        MultiPermissionResponseModel myPermissionsResponse = myPermissionsService.getMyPermissions(projectName, null, null, null);

        Map<String, PermissionModel> permissionsMap = myPermissionsResponse.extractPermissions();
        assertTrue(permissionsMap.containsKey("CREATE_ISSUE"));
        assertTrue(permissionsMap.containsKey("CLOSE_ISSUE"));
    }

}
