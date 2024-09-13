/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.service;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.jira.common.server.model.IssueSearchRequestModel;
import com.blackduck.integration.jira.common.server.model.IssueSearchResponseModel;
import com.blackduck.integration.rest.HttpUrl;

public class IssueSearchService {
    public static final String API_PATH = "/rest/api/2/search";

    private final JiraApiClient jiraCloudService;

    public IssueSearchService(JiraApiClient jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public IssueSearchResponseModel findIssuesByDescription(String projectKey, String issueType, String descriptionSearchTerm) throws IntegrationException {
        String jql = createProjectAndIssueTypeJqlForSearchTerm(projectKey, issueType, "description", descriptionSearchTerm);
        return queryForIssues(jql);
    }

    public IssueSearchResponseModel findIssuesByComment(String projectKey, String issueType, String commentSerchTerm) throws IntegrationException {
        String jql = createProjectAndIssueTypeJqlForSearchTerm(projectKey, issueType, "comment", commentSerchTerm);
        return queryForIssues(jql);
    }

    public IssueSearchResponseModel queryForIssues(String jql) throws IntegrationException {
        return queryForIssuePage(jql, null, null);
    }

    public IssueSearchResponseModel queryForIssuePage(String jql, Integer startAt, Integer maxResults) throws IntegrationException {
        IssueSearchRequestModel requestModel = IssueSearchRequestModel.paged(jql, startAt, maxResults);
        return findIssues(requestModel);
    }

    private IssueSearchResponseModel findIssues(IssueSearchRequestModel requestModel) throws IntegrationException {
        return jiraCloudService.post(requestModel, createApiUri(), IssueSearchResponseModel.class);
    }

    private String createProjectAndIssueTypeJqlForSearchTerm(String projectKey, String issueType, String fieldName, String searchTerm) {
        return String.format("project = %s AND issuetype = %s AND %s ~ '%s'", projectKey, issueType, fieldName, searchTerm);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraCloudService.getBaseUrl() + API_PATH);
    }

}
