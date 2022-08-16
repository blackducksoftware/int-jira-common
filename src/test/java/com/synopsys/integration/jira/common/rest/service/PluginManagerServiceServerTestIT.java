package com.synopsys.integration.jira.common.rest.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.JiraServerParameterizedTestIT;
import com.synopsys.integration.jira.common.server.JiraServerServiceTestUtility;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

class PluginManagerServiceServerTestIT extends JiraServerParameterizedTestIT {

    @ParameterizedTest
    @MethodSource("getParameters")
    void createIssueTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        boolean appInstalled = pluginManagerService.isAppInstalled("com.synopsys.integration.alert");
        System.out.println("App is installed " + appInstalled);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    void retrievePluginTokenJiraServerCredentialClientTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        String pluginToken = pluginManagerService.retrievePluginToken();
        assertNotNull(pluginToken);
    }
}
