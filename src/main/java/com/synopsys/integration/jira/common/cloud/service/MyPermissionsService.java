/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.service;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

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

    public MultiPermissionResponseModel getMyPermissions(String permission) throws IntegrationException {
        return getMyPermissions(Collections.singletonList(permission), null, null, null, null, null, null);
    }

    public MultiPermissionResponseModel getMyPermissions(
        Collection<String> permissions,
        @Nullable String projectKey,
        @Nullable String projectId,
        @Nullable String issueKey,
        @Nullable String issueId,
        @Nullable String projectUuid,
        @Nullable String projectConfigurationUuid
    ) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);

        String permissionsCSV = StringUtils.join(permissions, ",");
        JiraRequest.Builder jiraRequestBuilder = JiraRequestFactory.createDefaultBuilder()
                                                     .url(httpUrl)
                                                     .addQueryParameter("permissions", permissionsCSV)
                                                     .addQueryParamIfValueNotBlank("projectKey", projectKey)
                                                     .addQueryParamIfValueNotBlank("projectId", projectId)
                                                     .addQueryParamIfValueNotBlank("issueKey", issueKey)
                                                     .addQueryParamIfValueNotBlank("issueId", issueId)
                                                     .addQueryParamIfValueNotBlank("projectUuid", projectUuid)
                                                     .addQueryParamIfValueNotBlank("projectConfigurationUuid", projectConfigurationUuid);

        return jiraApiClient.get(jiraRequestBuilder.build(), MultiPermissionResponseModel.class);
    }

}
