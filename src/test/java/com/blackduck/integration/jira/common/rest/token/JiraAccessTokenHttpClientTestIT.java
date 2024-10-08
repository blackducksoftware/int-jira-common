package com.blackduck.integration.jira.common.rest.token;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.IntegrationsTestConstants;
import com.blackduck.integration.jira.common.model.response.IssueCreationResponseModel;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.server.JiraServerServiceTestUtility;
import com.blackduck.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.blackduck.integration.jira.common.server.model.IssueCreationRequestModel;
import com.blackduck.integration.jira.common.server.service.IssueService;
import com.blackduck.integration.jira.common.server.service.JiraServerServiceFactory;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.google.gson.Gson;

@Tag(IntegrationsTestConstants.INTEGRATION_TEST)
class JiraAccessTokenHttpClientTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String projectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);
    private final String reporter = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
    private final String issueTypeName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_ISSUE_TYPE);
    private final String url = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL);
    private final String accessToken = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_PERSONAL_ACCESS_TOKEN);
    private final PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);
    private final Gson gson = new Gson();
    private final ProxyInfo proxyInfo = ProxyInfo.NO_PROXY_INFO;
    private final AuthenticationSupport authenticationSupport = new AuthenticationSupport();

    @Test
    void jiraServerTokenAuthTest() throws IntegrationException {
        JiraHttpClient jiraHttpClient = new JiraAccessTokenHttpClient(intLogger, gson, 120, true, proxyInfo, url, authenticationSupport, accessToken);
        testService(jiraHttpClient);
    }

    private void testService(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription("Test description");

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        IssueCreationResponseModel issue = issueService.createIssue(issueCreationRequestModel);
        assertNotNull(issue, "Expected an issue to be created.");
    }

}
