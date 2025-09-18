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
import com.blackduck.integration.jira.common.rest.RestApiVersion;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.rest.HttpUrl;

public class IssueTypeService {

    private final JiraApiClient jiraApiClient;
    private final RestApiVersion restApiVersion;

    public IssueTypeService(JiraApiClient jiraApiClient, RestApiVersion restApiVersion) {
        this.jiraApiClient = jiraApiClient;
        this.restApiVersion = restApiVersion;
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
        return new HttpUrl(jiraApiClient.getBaseUrl() + createUriPath());
    }

    private String createUriPath() {
        return "/rest/api/" + restApiVersion.getVersion() + "/issuetype";
    }

}
