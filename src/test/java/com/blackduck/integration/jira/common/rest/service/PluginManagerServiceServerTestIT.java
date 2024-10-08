package com.blackduck.integration.jira.common.rest.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.IntegrationsTestConstants;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.server.JiraServerServiceTestUtility;
import com.blackduck.integration.jira.common.server.service.JiraServerServiceFactory;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;

@Tag(IntegrationsTestConstants.INTEGRATION_TEST)
class PluginManagerServiceServerTestIT {

    @Test
    void createIssueTest() throws IntegrationException {
        JiraHttpClient jiraHttpClient = JiraServerServiceTestUtility.createJiraCredentialClient(new PrintStreamIntLogger(System.out, LogLevel.WARN));
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        boolean appInstalled = pluginManagerService.isAppInstalled("com.blackduck.integration.alert");
        System.out.println("App is installed " + appInstalled);
    }

    @Test
    void retrievePluginTokenJiraServerCredentialClientTest() throws IntegrationException {
        JiraHttpClient jiraHttpClient = JiraServerServiceTestUtility.createJiraCredentialClient(new PrintStreamIntLogger(System.out, LogLevel.WARN));
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        PluginManagerService pluginManagerService = serviceFactory.createPluginManagerService();

        String pluginToken = pluginManagerService.retrievePluginToken();
        assertNotNull(pluginToken);
    }
}
