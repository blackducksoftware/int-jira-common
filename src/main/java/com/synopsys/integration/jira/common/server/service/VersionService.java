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

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.jira.common.server.model.VersionRequestModel;
import com.synopsys.integration.jira.common.server.model.VersionResponseModel;
import com.synopsys.integration.rest.HttpUrl;

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
