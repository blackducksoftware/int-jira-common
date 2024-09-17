/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.service;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.request.VersionRequestModel;
import com.blackduck.integration.jira.common.model.response.VersionResponseModel;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.rest.HttpUrl;

public class VersionService {
    public static final String API_PATH = "/rest/api/2/version";

    private final JiraApiClient jiraApiClient;

    public VersionService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public VersionResponseModel getVersion(String id) throws IntegrationException {
        HttpUrl url = createApiUrlWithId(id);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(url);
        return jiraApiClient.get(request, VersionResponseModel.class);
    }

    public void deleteVersion(String id) throws IntegrationException {
        HttpUrl url = createApiUrlWithId(id);
        jiraApiClient.delete(url);
    }

    public VersionResponseModel createVersion(String name, String projectId) throws IntegrationException {
        HttpUrl url = createApiUrl();
        return jiraApiClient.post(new VersionRequestModel(name, projectId), url, VersionResponseModel.class);
    }

    public VersionResponseModel createVersion(VersionRequestModel versionRequestModel) throws IntegrationException {
        HttpUrl url = createApiUrl();
        return jiraApiClient.post(versionRequestModel, url, VersionResponseModel.class);
    }

    private HttpUrl createApiUrl() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);
    }

    private HttpUrl createApiUrlWithId(String id) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s", createApiUrl(), id));
    }
}
