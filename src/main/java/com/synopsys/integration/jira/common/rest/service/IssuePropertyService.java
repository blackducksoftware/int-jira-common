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
package com.synopsys.integration.jira.common.rest.service;

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
    private JiraService jiraService;

    public IssuePropertyService(Gson gson, JiraService jiraService) {
        this.gson = gson;
        this.jiraService = jiraService;
    }

    public IssuePropertyKeysResponseModel getPropertyKeys(String issueKey) throws IntegrationException {
        String uri = createApiUri(issueKey);
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(uri)
                              .build();
        return jiraService.get(request, IssuePropertyKeysResponseModel.class);
    }

    public IssuePropertyResponseModel getProperty(String issueKey, String propertyKey) throws IntegrationException {
        String uri = createApiUriWithKey(issueKey, propertyKey);
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(uri)
                              .build();
        return jiraService.get(request, IssuePropertyResponseModel.class);
    }

    public Response setProperty(String issueKey, String propertyKey, Serializable propertyValue) throws IntegrationException {
        String json = gson.toJson(propertyValue);
        return setProperty(issueKey, propertyKey, json);
    }

    public Response setProperty(String issueKey, String propertyKey, String jsonPropertyValue) throws IntegrationException {
        String uri = createApiUriWithKey(issueKey, propertyKey);
        return jiraService.put(jsonPropertyValue, uri);
    }

    private String createApiUriWithKey(String issueKey, String propertyKey) {
        return createApiUri(issueKey) + "/" + propertyKey;
    }

    private String createApiUri(String issueKey) {
        return jiraService.getBaseUrl() + API_PATH + "/" + issueKey + API_PATH_PROPERTIES_PIECE;
    }

}
