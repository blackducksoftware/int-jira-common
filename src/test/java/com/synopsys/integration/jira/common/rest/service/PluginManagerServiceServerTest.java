package com.synopsys.integration.jira.common.rest.service;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.server.JiraServerServiceTest;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

public class PluginManagerServiceServerTest extends JiraServerServiceTest {

    @Test
    public void createIssueTest() throws IntegrationException {
        validateConfiguration();

        JiraServerServiceFactory serviceFactory = createServiceFactory();
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        boolean appInstalled = pluginManagerService.isAppInstalled(getEnvUsername(), getEnvPassword(), "com.synopsys.integration.alert");
        System.out.println("App is installed " + appInstalled);
    }
}
