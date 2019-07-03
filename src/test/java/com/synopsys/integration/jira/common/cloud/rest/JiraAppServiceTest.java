package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.PluginResponseModel;
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

    @Test
    public void getInstalledAppsTest() throws IntegrationException {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final JiraAppService jiraAppService = serviceFactory.createJiraAppService();

        String userEmail = getEnvUserEmail();
        String apiToken = getEnvApiToken();

        final Optional<PluginResponseModel> fakeApp = jiraAppService.getInstalledApp(userEmail, apiToken, "not.a.real.key");
        assertFalse(fakeApp.isPresent(), "Expected app to not be installed");

        final Response installResponse = jiraAppService.installApp("Test", APP_URI, userEmail, apiToken);
        installResponse.throwExceptionForError();

        final InstalledAppsResponseModel installedApps = jiraAppService.getInstalledApps(userEmail, apiToken);
        final List<PluginResponseModel> allInstalledPlugins = installedApps.getPlugins();
        final List<PluginResponseModel> userInstalledPlugins = allInstalledPlugins
                                                                   .stream()
                                                                   .filter(plugin -> plugin.getUserInstalled())
                                                                   .collect(Collectors.toList());
        assertTrue(userInstalledPlugins.size() < allInstalledPlugins.size(), "Expected fewer user-installed plugins than total plugins");

        final Response uninstallResponse = jiraAppService.uninstallApp(APP_KEY, userEmail, apiToken);
        uninstallResponse.throwExceptionForError();
    }

}
