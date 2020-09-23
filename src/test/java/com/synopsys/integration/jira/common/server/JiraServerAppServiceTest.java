package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.jira.common.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.model.response.PluginResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.PluginManagerService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.rest.RestConstants;
import com.synopsys.integration.rest.exception.IntegrationRestException;

public class JiraServerAppServiceTest extends JiraServerParameterizedTest {
    private static final String APP_KEY = "com.synopsys.integration.alert";
    private static final String APP_SERVER_URI = "https://blackducksoftware.github.io/alert-issue-property-indexer/JiraServerApp/1.0.0/atlassian-plugin.xml";

    // If these tests start breaking, verify the 'Barcharts for Jira' plugin is still free and available.
    private static final String APP_FREE_MARKETPLACE_KEY = "com.tngtech.gadgets.jira-barchart-gadget-plugin";

    @AfterEach
    public void waitForUninstallToFinish() throws InterruptedException {
        Thread.sleep(1000);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void installServerAppTest(JiraHttpClient jiraHttpClient) throws Exception {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        int installResponse = pluginManagerService.installMarketplaceServerApp(APP_FREE_MARKETPLACE_KEY);
        assertTrue(isStatusCodeSuccess(installResponse), "Expected a 2xx response code, but was: " + installResponse);
        Thread.sleep(3000);
        int uninstallResponse = pluginManagerService.uninstallApp(APP_FREE_MARKETPLACE_KEY);
        assertTrue(isStatusCodeSuccess(uninstallResponse), "Expected a 2xx response code, but was: " + uninstallResponse);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    @Disabled
    // Disabled because development mode will likely not be turned on most of the time.
    public void installServerDevelopmentAppTest(JiraHttpClient jiraHttpClient) throws Exception {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        int installResponse = pluginManagerService.installDevelopmentApp("Test", APP_SERVER_URI);
        assertTrue(isStatusCodeSuccess(installResponse), "Expected a 2xx response code, but was: " + installResponse);
        Thread.sleep(3000);
        int uninstallResponse = pluginManagerService.uninstallApp(APP_KEY);
        assertTrue(isStatusCodeSuccess(uninstallResponse), "Expected a 2xx response code, but was: " + uninstallResponse);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getInstalledAppsTest(JiraHttpClient jiraHttpClient) throws Exception {
        JiraServerServiceTestUtility.validateConfiguration();
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        Optional<PluginResponseModel> fakeApp = pluginManagerService.getInstalledApp("not.a.real.key");
        assertFalse(fakeApp.isPresent(), "Expected app to not be installed");

        int installResponse = pluginManagerService.installMarketplaceServerApp(APP_FREE_MARKETPLACE_KEY);
        throwExceptionForError(installResponse);
        Thread.sleep(3000);
        InstalledAppsResponseModel installedApps = pluginManagerService.getInstalledApps();

        List<PluginResponseModel> allInstalledPlugins = installedApps.getPlugins();
        List<PluginResponseModel> userInstalledPlugins = allInstalledPlugins
                                                             .stream()
                                                             .filter(plugin -> plugin.getUserInstalled())
                                                             .collect(Collectors.toList());
        assertTrue(userInstalledPlugins.size() < allInstalledPlugins.size(), "Expected fewer user-installed plugins than total plugins");

        int uninstallResponse = pluginManagerService.uninstallApp(APP_FREE_MARKETPLACE_KEY);
        throwExceptionForError(uninstallResponse);
    }

    private boolean isStatusCodeSuccess(int status) {
        return status >= RestConstants.OK_200 && status < RestConstants.MULT_CHOICE_300;
    }

    public boolean isStatusCodeError(int status) {
        return status >= RestConstants.BAD_REQUEST_400;
    }

    public void throwExceptionForError(int status) throws IntegrationRestException {
        if (isStatusCodeError(status)) {
            throw new IntegrationRestException(status, "statusMessage", "Body", "message");
        }
    }

}
