package com.blackduck.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.response.IssueCreationResponseModel;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.service.IssuePropertyService;
import com.blackduck.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.blackduck.integration.jira.common.server.model.IssueCreationRequestModel;
import com.blackduck.integration.jira.common.server.model.IssueSearchIssueComponent;
import com.blackduck.integration.jira.common.server.model.IssueSearchResponseModel;
import com.blackduck.integration.jira.common.server.service.IssueSearchService;
import com.blackduck.integration.jira.common.server.service.IssueService;
import com.blackduck.integration.jira.common.server.service.JiraServerServiceFactory;
import com.blackduck.integration.jira.common.test.TestProperties;
import com.blackduck.integration.jira.common.test.TestPropertyKey;
import com.google.gson.Gson;

public class IssueSearchServiceTestIT extends JiraServerParameterizedTestIT {
    private final TestProperties testProperties = TestProperties.loadTestProperties();
    private final String projectName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_TEST_PROJECT_NAME);
    private final String reporter = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_USERNAME);
    private final String issueTypeName = testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_ISSUE_TYPE);

    @ParameterizedTest
    @MethodSource("getParameters")
    public void queryForIssuesTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        String searchTerm = UUID.randomUUID().toString();
        String description = "Example description containing a special word to search on: " + searchTerm;
        IssueCreationResponseModel issue = createIssue(issueService, projectName, description);

        try {
            IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
            String jql = "project = '" + projectName + "' AND description ~ '" + searchTerm + "'";
            IssueSearchResponseModel issueSearchResponseModel = issueSearchService.queryForIssues(jql);

            List<IssueSearchIssueComponent> issues = issueSearchResponseModel.getIssues();
            assertEquals(1, issues.size());
            String foundIssueKey = issues
                                       .stream()
                                       .findFirst()
                                       .map(IssueSearchIssueComponent::getKey)
                                       .orElse(null);
            assertEquals(issue.getKey(), foundIssueKey);
        } finally {
            issueService.deleteIssue(issue.getId());
        }
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getParameters")
    public void findIssueByPropertyTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
        IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();

        IssueCreationResponseModel issue = createIssue(issueService, projectName, "Test description");

        Gson gson = new Gson();
        TestPropertyClass testPropertyClass = new TestPropertyClass("innerValue");
        String testPropertyClassString = gson.toJson(testPropertyClass);

        String issuePropertyKey = "test-property-key";
        try {
            issuePropertyService.setProperty(issue.getKey(), issuePropertyKey, testPropertyClassString);

            String jql = "project = '" + projectName + "' AND issue.property[" + issuePropertyKey + "]." + TestPropertyClass.THING_TO_SEARCH_FOR + " = '" + testPropertyClass.getThingToSearchFor() + "'";
            IssueSearchResponseModel issueSearchResponseModel = issueSearchService.queryForIssues(jql);

            List<IssueSearchIssueComponent> issues = issueSearchResponseModel.getIssues();
            assertEquals(1, issues.size());
            String foundIssueKey = issueSearchResponseModel.getIssues()
                                       .stream()
                                       .findFirst()
                                       .map(IssueSearchIssueComponent::getKey)
                                       .orElse(null);
            assertEquals(issue.getKey(), foundIssueKey);
        } finally {
            issueService.deleteIssue(issue.getId());
        }
    }

    private IssueCreationResponseModel createIssue(IssueService issueService, String projectName, String description) throws IntegrationException {
        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary("Created by a JUnit Test in int-jira-common: " + UUID.randomUUID().toString());
        issueRequestModelFieldsBuilder.setDescription(description);

        IssueCreationRequestModel issueCreationRequestModel = new IssueCreationRequestModel(reporter, issueTypeName, projectName, issueRequestModelFieldsBuilder);
        return issueService.createIssue(issueCreationRequestModel);
    }

    private static final class TestPropertyClass {
        public static final String THING_TO_SEARCH_FOR = "thingToSearchFor";

        private final String thingToSearchFor;

        public TestPropertyClass(String thingToSearchFor) {
            this.thingToSearchFor = thingToSearchFor;
        }

        public String getThingToSearchFor() {
            return thingToSearchFor;
        }

    }

}
