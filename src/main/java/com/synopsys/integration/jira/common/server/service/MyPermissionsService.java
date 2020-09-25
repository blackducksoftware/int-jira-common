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
