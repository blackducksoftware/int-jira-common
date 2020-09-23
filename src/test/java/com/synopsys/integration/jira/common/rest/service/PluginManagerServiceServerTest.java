package com.synopsys.integration.jira.common.rest.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.JiraServerParameterizedTest;
import com.synopsys.integration.jira.common.server.JiraServerServiceTestUtility;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

public class PluginManagerServiceServerTest extends JiraServerParameterizedTest {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void createIssueTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        boolean appInstalled = pluginManagerService.isAppInstalled("com.synopsys.integration.alert");
        System.out.println("App is installed " + appInstalled);
    }
}
