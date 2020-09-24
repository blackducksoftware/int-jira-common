/**
 * int-jira-common
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.jira.common.rest.service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.reflect.TypeToken;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.PermissionModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class MyPermissionsService {
    private static final String API_PATH = "/rest/api/2/mypermissions";

    private final JiraApiClient jiraApiClient;

    public MyPermissionsService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public Map<String, PermissionModel> getMyPermissions(String permission) throws IntegrationException {
        return getMyPermissions(Collections.singletonList(permission), null, null, null, null, null, null);
    }

    public Map<String, PermissionModel> getMyPermissions(
        List<String> permissions,
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
                                                     .addQueryParameter("permissions", permissionsCSV);

        addQueryParamIfNotBlank(jiraRequestBuilder, "projectKey", projectKey);
        addQueryParamIfNotBlank(jiraRequestBuilder, "projectId", projectId);
        addQueryParamIfNotBlank(jiraRequestBuilder, "issueKey", issueKey);
        addQueryParamIfNotBlank(jiraRequestBuilder, "issueId", issueId);
        addQueryParamIfNotBlank(jiraRequestBuilder, "projectUuid", projectUuid);
        addQueryParamIfNotBlank(jiraRequestBuilder, "projectConfigurationUuid", projectConfigurationUuid);

        JiraResponse jiraResponse = jiraApiClient.get(jiraRequestBuilder.build());

        IntJsonTransformer jsonTransformer = jiraApiClient.getJsonTransformer();
        Type responseType = new TypeToken<Map<String, PermissionModel>>() {}.getType();
        return jsonTransformer.getComponentAs(jiraResponse.getContent(), responseType);
    }

    private void addQueryParamIfNotBlank(JiraRequest.Builder jiraRequestBuilder, String keyName, String keyValue) {
        if (StringUtils.isBlank(keyValue)) {
            jiraRequestBuilder.addQueryParameter(keyName, keyValue);
        }
    }

}
