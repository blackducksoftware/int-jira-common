/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.service;

import java.util.List;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.IssueTypeRequestModel;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.response.IssueTypeResponseModel;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.rest.HttpUrl;

public class IssueTypeService {
    private static final String API_PATH = "/rest/api/2/issuetype";

    private final JiraApiClient jiraApiClient;

    public IssueTypeService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public List<IssueTypeResponseModel> getAllIssueTypes() throws IntegrationException {
        HttpUrl uri = createApiUri();
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(uri);
        return jiraApiClient.getList(request, IssueTypeResponseModel.class);
    }

    public IssueTypeResponseModel createIssueType(IssueTypeRequestModel issueTypeRequestModel) throws IntegrationException {
        HttpUrl uri = createApiUri();
        return jiraApiClient.post(issueTypeRequestModel, uri, IssueTypeResponseModel.class);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);
    }

}
