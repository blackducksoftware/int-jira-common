package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.model.response.PluginResponseModel;
import com.synopsys.integration.jira.common.rest.service.PluginManagerService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.rest.request.Response;

public class JiraServerAppServiceTest extends JiraServerServiceTest {
    private static final String APP_KEY = "com.synopsys.integration.alert";
    private static final String APP_SERVER_URI = "https://blackducksoftware.github.io/alert-issue-property-indexer/JiraServerApp/1.0.0/atlassian-plugin.xml";
    private static final String APP_SLACK_KEY = "com.atlassian.jira.plugins.jira-slack-server-integration-plugin";

    @AfterEach
    public void waitForUninstallToFinish() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    public void installServerAppTest() throws Exception {
        validateConfiguration();
        JiraServerServiceFactory serviceFactory = createServiceFactory();
        PluginManagerService pluginManagerService = serviceFactory.createAppService();

        String username = getEnvUsername();
        String password = getEnvPassword();

        Response installResponse = pluginManagerService.installApp(APP_SLACK_KEY, username, password);
        assertTrue(installResponse.isStatusCodeOkay(), "Expected a 2xx response code, but was: " + installResponse.getStatusCode());
        Thread.sleep(1000);
        Response uninstallResponse = pluginManagerService.uninstallApp(APP_SLACK_KEY, username, password);
        assertTrue(uninstallResponse.isStatusCodeOkay(), "Expected a 2xx response code, but was: " + uninstallResponse.getStatusCode());
    }

    @Test
    @Disabled
    // Disabled because development mode will likely not be turned on most of the time.
    public void installServerDevelopmentAppTest() throws Exception {
        validateConfiguration();
        JiraServerServiceFactory serviceFactory = createServiceFactory();
        PluginManagerService pluginManagerService = serviceFactory.createAppService();

        String userEmail = getEnvUsername();
        String apiToken = getEnvPassword();

        Response installResponse = pluginManagerService.installDevelopmentApp(
            "Test",
            APP_SERVER_URI,
            userEmail,
            apiToken
        );
        assertTrue(installResponse.isStatusCodeOkay(), "Expected a 2xx response code, but was: " + installResponse.getStatusCode());
        Thread.sleep(1000);
        Response uninstallResponse = pluginManagerService.uninstallApp(APP_KEY, userEmail, apiToken);
        assertTrue(uninstallResponse.isStatusCodeOkay(), "Expected a 2xx response code, but was: " + uninstallResponse.getStatusCode());
    }

    @Test
    public void getInstalledAppsTest() throws Exception {
        validateConfiguration();
        JiraServerServiceFactory serviceFactory = createServiceFactory();
        PluginManagerService pluginManagerService = serviceFactory.createAppService();

        String username = getEnvUsername();
        String password = getEnvPassword();

        Optional<PluginResponseModel> fakeApp = pluginManagerService.getInstalledApp(username, password, "not.a.real.key");
        assertFalse(fakeApp.isPresent(), "Expected app to not be installed");

        Response installResponse = pluginManagerService.installApp(APP_SLACK_KEY, username, password);
        installResponse.throwExceptionForError();
        Thread.sleep(1000);
        InstalledAppsResponseModel installedApps = pluginManagerService.getInstalledApps(username, password);

        List<PluginResponseModel> allInstalledPlugins = installedApps.getPlugins();
        List<PluginResponseModel> userInstalledPlugins = allInstalledPlugins
                                                             .stream()
                                                             .filter(plugin -> plugin.getUserInstalled())
                                                             .collect(Collectors.toList());
        assertTrue(userInstalledPlugins.size() < allInstalledPlugins.size(), "Expected fewer user-installed plugins than total plugins");

        Response uninstallResponse = pluginManagerService.uninstallApp(APP_SLACK_KEY, username, password);
        uninstallResponse.throwExceptionForError();
    }

}
