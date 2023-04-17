/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.server.service;

import javax.annotation.Nullable;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.MultiPermissionResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

public class MyPermissionsService {
    private static final String API_PATH = "/rest/api/2/mypermissions";

    private final JiraApiClient jiraApiClient;

    public MyPermissionsService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public MultiPermissionResponseModel getMyPermissions() throws IntegrationException {
        return getMyPermissions(null, null, null, null);
    }

    public MultiPermissionResponseModel getMyPermissions(@Nullable String projectKey, @Nullable String projectId, @Nullable String issueKey, @Nullable String issueId) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);

        JiraRequest.Builder jiraRequestBuilder = JiraRequestFactory.createDefaultBuilder()
                                                     .url(httpUrl)
                                                     .addQueryParamIfValueNotBlank("projectKey", projectKey)
                                                     .addQueryParamIfValueNotBlank("projectId", projectId)
                                                     .addQueryParamIfValueNotBlank("issueKey", issueKey)
                                                     .addQueryParamIfValueNotBlank("issueId", issueId);

        return jiraApiClient.get(jiraRequestBuilder.build(), MultiPermissionResponseModel.class);
    }

}
