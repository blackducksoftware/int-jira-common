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
package com.synopsys.integration.jira.common.cloud.service;

import java.io.Serializable;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;

public class IssuePropertyService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_PROPERTIES_PIECE = "/properties";

    private Gson gson;
    private JiraCloudService jiraCloudService;

    public IssuePropertyService(final Gson gson, final JiraCloudService jiraCloudService) {
        this.gson = gson;
        this.jiraCloudService = jiraCloudService;
    }

    public IssuePropertyKeysResponseModel getPropertyKeys(final String issueKey) throws IntegrationException {
        final String uri = createApiUri(issueKey);
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(uri)
                              .build();
        return jiraCloudService.get(request, IssuePropertyKeysResponseModel.class);
    }

    public IssuePropertyResponseModel getProperty(final String issueKey, final String propertyKey) throws IntegrationException {
        final String uri = createApiUriWithKey(issueKey, propertyKey);
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(uri)
                              .build();
        return jiraCloudService.get(request, IssuePropertyResponseModel.class);
    }

    public Response setProperty(final String issueKey, final String propertyKey, final Serializable propertyValue) throws IntegrationException {
        final String json = gson.toJson(propertyValue);
        return setProperty(issueKey, propertyKey, json);
    }

    public Response setProperty(final String issueKey, final String propertyKey, final String jsonPropertyValue) throws IntegrationException {
        final String uri = createApiUriWithKey(issueKey, propertyKey);
        return jiraCloudService.put(jsonPropertyValue, uri);
    }

    private String createApiUriWithKey(final String issueKey, final String propertyKey) {
        return createApiUri(issueKey) + "/" + propertyKey;
    }

    private String createApiUri(final String issueKey) {
        return jiraCloudService.getBaseUrl() + API_PATH + "/" + issueKey + API_PATH_PROPERTIES_PIECE;
    }

}
