/**
 * int-jira-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.jira.common.server.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.JiraService;
import com.synopsys.integration.jira.common.server.model.IssueSearchRequestModel;
import com.synopsys.integration.jira.common.server.model.IssueSearchResponseModel;

public class IssueSearchService {
    public static final String API_PATH = "/rest/api/2/search";

    private JiraService jiraCloudService;

    public IssueSearchService(JiraService jiraCloudService) {
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

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

}
