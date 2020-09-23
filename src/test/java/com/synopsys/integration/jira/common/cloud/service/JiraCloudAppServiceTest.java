package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.model.response.PluginResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.PluginManagerService;
import com.synopsys.integration.rest.RestConstants;
import com.synopsys.integration.rest.exception.IntegrationRestException;

public class JiraCloudAppServiceTest extends JiraCloudParameterizedTest {
    private static final int WAIT_TIME = 1000;
    private static final String APP_KEY = "com.synopsys.integration.alert";
    private static final String APP_CLOUD_URI = "https://blackducksoftware.github.io/alert-issue-property-indexer/JiraCloudApp/1.0.0/atlassian-connect.json";

    @AfterEach
    public void waitForUninstallToFinish() throws InterruptedException {
        Thread.sleep(JiraCloudAppServiceTest.WAIT_TIME);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void installMarketplaceAppTest(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        int installResponse = pluginManagerService.installMarketplaceCloudApp(APP_KEY);
        assertTrue(isStatusCodeSuccess(installResponse), "Expected a 2xx response code, but was: " + installResponse);
        Thread.sleep(JiraCloudAppServiceTest.WAIT_TIME);
        int uninstallResponse = pluginManagerService.uninstallApp(APP_KEY);
        assertTrue(isStatusCodeSuccess(uninstallResponse), "Expected a 2xx response code, but was: " + uninstallResponse);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    @Disabled
    // Disabled because development mode will likely not be turned on most of the time.
    public void installCloudDevelopmentAppTest(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        int installResponse = pluginManagerService.installDevelopmentApp("Test", APP_CLOUD_URI);
        assertTrue(isStatusCodeSuccess(installResponse), "Expected a 2xx response code, but was: " + installResponse);
        Thread.sleep(JiraCloudAppServiceTest.WAIT_TIME);
        int uninstallResponse = pluginManagerService.uninstallApp(APP_KEY);
        assertTrue(isStatusCodeSuccess(uninstallResponse), "Expected a 2xx response code, but was: " + uninstallResponse);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getInstalledAppsTest(JiraHttpClient jiraHttpClient) throws IntegrationException, InterruptedException {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        Optional<PluginResponseModel> fakeApp = pluginManagerService.getInstalledApp("not.a.real.key");
        assertFalse(fakeApp.isPresent(), "Expected app to not be installed");

        int installResponse = pluginManagerService.installMarketplaceCloudApp(APP_KEY);
        throwExceptionForError(installResponse);

        InstalledAppsResponseModel installedApps = pluginManagerService.getInstalledApps();
        List<PluginResponseModel> allInstalledPlugins = installedApps.getPlugins();
        List<PluginResponseModel> userInstalledPlugins = allInstalledPlugins
                                                             .stream()
                                                             .filter(plugin -> plugin.getUserInstalled())
                                                             .collect(Collectors.toList());
        assertTrue(userInstalledPlugins.size() < allInstalledPlugins.size(), "Expected fewer user-installed plugins than total plugins");

        Thread.sleep(JiraCloudAppServiceTest.WAIT_TIME);
        int uninstallResponse = pluginManagerService.uninstallApp(APP_KEY);
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
