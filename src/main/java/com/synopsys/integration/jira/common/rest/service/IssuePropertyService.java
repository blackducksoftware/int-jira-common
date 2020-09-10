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

import java.io.Serializable;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.synopsys.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.rest.HttpUrl;

public class IssuePropertyService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_PROPERTIES_PIECE = "/properties";

    private final Gson gson;
    private final JiraApiClient jiraApiClient;

    public IssuePropertyService(Gson gson, JiraApiClient jiraApiClient) {
        this.gson = gson;
        this.jiraApiClient = jiraApiClient;
    }

    public IssuePropertyKeysResponseModel getPropertyKeys(String issueKey) throws IntegrationException {
        String url = createApiUri(issueKey);
        HttpUrl httpUrl = new HttpUrl(url);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(httpUrl)
                                  .build();
        return jiraApiClient.get(request, IssuePropertyKeysResponseModel.class);
    }

    public IssuePropertyResponseModel getProperty(String issueKey, String propertyKey) throws IntegrationException {
        HttpUrl url = createApiUriWithKey(issueKey, propertyKey);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(url)
                                  .build();
        return jiraApiClient.get(request, IssuePropertyResponseModel.class);
    }

    public void setProperty(String issueKey, String propertyKey, Serializable propertyValue) throws IntegrationException {
        String json = gson.toJson(propertyValue);
        setProperty(issueKey, propertyKey, json);
    }

    public void setProperty(String issueKey, String propertyKey, String jsonPropertyValue) throws IntegrationException {
        HttpUrl url = createApiUriWithKey(issueKey, propertyKey);
        jiraApiClient.put(jsonPropertyValue, url);
    }

    private HttpUrl createApiUriWithKey(String issueKey, String propertyKey) throws IntegrationException {
        return new HttpUrl(createApiUri(issueKey) + "/" + propertyKey);
    }

    private String createApiUri(String issueKey) {
        return jiraApiClient.getBaseUrl() + API_PATH + "/" + issueKey + API_PATH_PROPERTIES_PIECE;
    }

}
