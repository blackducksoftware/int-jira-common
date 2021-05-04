/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.IssueSearchRequestModel;
import com.synopsys.integration.jira.common.cloud.model.IssueSearchResponseModel;
import com.synopsys.integration.jira.common.enumeration.ExpandableTypes;
import com.synopsys.integration.jira.common.enumeration.QueryValidationStrategy;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

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
        List<ExpandableTypes> typesToExpand = new ArrayList<>();
        typesToExpand.addAll(Arrays.asList(ExpandableTypes.values()));
        List<String> properties = Collections.emptyList();

        IssueSearchRequestModel requestModel = new IssueSearchRequestModel(jql, startAt, maxResults, IssueSearchRequestModel.ALL_FIELDS_LIST, QueryValidationStrategy.STRICT, typesToExpand, properties, false);
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
