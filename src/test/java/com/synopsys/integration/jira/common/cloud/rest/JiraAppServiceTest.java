package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.cloud.rest.service.JiraAppService;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.rest.request.Response;

public class JiraAppServiceTest extends JiraServiceTest {
    private static final String APP_KEY = "com.synopsys.integration.alert";
    private static final String APP_URI = "https://raw.githubusercontent.com/blackducksoftware/blackduck-alert/d94fafae9fba14e3b068acdc9ff458d77d27ca63/src/main/resources/jira/atlassian-connect.json";

    @Test
    public void installAppTest() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final JiraAppService jiraAppService = serviceFactory.createJiraAppService();

        String userEmail = getEnvUserEmail();
        String apiToken = getEnvApiToken();

        final Response installResponse = jiraAppService.installApp(
            "Test",
            APP_URI,
            userEmail,
            apiToken
        );
        assertTrue(installResponse.isStatusCodeOkay(), "Expected a 2xx response code, but was: " + installResponse.getStatusCode());

        final Response uninstallResponse = jiraAppService.uninstallApp(APP_KEY, userEmail, apiToken);
        assertTrue(uninstallResponse.isStatusCodeOkay(), "Expected a 2xx response code, but was: " + uninstallResponse.getStatusCode());
    }

}
