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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.request.JiraRequestModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.component.IntRestComponent;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class JiraService {
    private final Gson gson;
    private final JiraHttpClient httpClient;
    private final IntJsonTransformer jsonTransformer;

    public JiraService(Gson gson, JiraHttpClient httpClient, IntJsonTransformer jsonTransformer) {
        this.gson = gson;
        this.httpClient = httpClient;
        this.jsonTransformer = jsonTransformer;
    }

    public String getBaseUrl() {
        return httpClient.getBaseUrl();
    }

    public <R extends JiraResponseModel> R get(JiraRequest jiraRequest, Class<R> responseClass) throws IntegrationException {
        return execute(jiraRequest, responseClass);
    }

    public JiraResponse get(JiraRequest jiraRequest) throws IntegrationException {
        return execute(jiraRequest);
    }

    public <R extends JiraResponseModel> List<R> getList(JiraRequest jiraRequest, Class<R> responseClass) throws IntegrationException {
        JiraResponse response = httpClient.execute(jiraRequest);
        JsonArray arrayResponse = gson.fromJson(response.getContent(), JsonArray.class);
        List<R> responseList = new LinkedList<>();
        for (JsonElement jsonElement : arrayResponse) {
            if (jsonElement.isJsonObject()) {
                R responseElement = jsonTransformer.getComponentAs(jsonElement.getAsJsonObject(), responseClass);
                responseList.add(responseElement);
            }
        }
        return responseList;
    }

    public <R extends JiraPageResponseModel> R getPage(JiraRequest jiraRequest, Class<R> responseClass, int offset) throws IntegrationException {
        return getPage(jiraRequest, responseClass, offset, JiraRequestFactory.DEFAULT_LIMIT);
    }

    public <R extends JiraPageResponseModel> R getPage(JiraRequest jiraRequest, Class<R> responseClass, int offset, int pageSize) throws IntegrationException {
        Map<String, Set<String>> populatedQueryParameters = jiraRequest.getPopulatedQueryParameters();

        Set<String> offsetValue = new HashSet<>();
        offsetValue.add(String.valueOf(offset));
        populatedQueryParameters.put("offset", offsetValue);

        Set<String> limitValue = new HashSet<>();
        limitValue.add(String.valueOf(pageSize));
        populatedQueryParameters.put("limit", limitValue);

        JiraResponse jiraResponse = httpClient.execute(jiraRequest);
        String content = jiraResponse.getContent();
        return gson.fromJson(content, responseClass);
    }

    public <R extends JiraResponseModel> R post(JiraRequestModel jiraRequestModel, HttpUrl url, Class<R> responseClass) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        JiraRequest request = createPostRequest(url, jsonRequestBody);
        return execute(request, responseClass);
    }

    public JiraResponse post(JiraRequestModel jiraRequestModel, HttpUrl url) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        JiraRequest request = createPostRequest(url, jsonRequestBody);
        return execute(request);
    }

    public <R extends JiraResponseModel> R put(JiraRequestModel jiraRequestModel, HttpUrl url, Class<R> responseClass) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        JiraRequest request = createPutRequest(url, jsonRequestBody);
        return execute(request, responseClass);
    }

    public JiraResponse put(JiraRequestModel jiraRequestModel, HttpUrl url) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        return put(jsonRequestBody, url);
    }

    public JiraResponse put(String jsonRequestBody, HttpUrl url) throws IntegrationException {
        JiraRequest request = createPutRequest(url, jsonRequestBody);
        return execute(request);
    }

    public JiraResponse delete(HttpUrl url) throws IntegrationException {
        JiraRequest request = createDeleteRequest(url);
        return execute(request);
    }

    public <R extends JiraResponseModel> R delete(HttpUrl url, Class<R> responseClass) throws IntegrationException {
        JiraRequest request = createDeleteRequest(url);
        return execute(request, responseClass);
    }

    public int executeReturnStatus(JiraRequest jiraRequest) throws IntegrationException {
        return execute(jiraRequest).getStatusCode();
    }

    public Map<String, String> getResponseHeaders(JiraRequest jiraRequest) throws IntegrationException {
        return execute(jiraRequest).getHeaders();
    }

    private <R extends JiraResponseModel> R execute(JiraRequest jiraRequest, Type type) throws IntegrationException {
        JiraResponse jiraResponse = httpClient.execute(jiraRequest);
        return parseResponse(jiraResponse, type);
    }

    private JiraResponse execute(JiraRequest request) throws IntegrationException {
        return httpClient.execute(request);
    }
    
    private JiraRequest.Builder createRequestBuilder(HttpUrl url, HttpMethod httpMethod) {
        return new JiraRequest.Builder()
                   .url(url)
                   .method(httpMethod);
    }

    private JiraRequest createPostRequest(HttpUrl url, String bodyContent) {
        return createRequestBuilder(url, HttpMethod.POST)
                   .bodyContent(bodyContent)
                   .build();
    }

    private JiraRequest createPutRequest(HttpUrl url, String bodyContent) {
        return createRequestBuilder(url, HttpMethod.PUT)
                   .bodyContent(bodyContent)
                   .build();
    }

    private JiraRequest createDeleteRequest(HttpUrl url) {
        return createRequestBuilder(url, HttpMethod.DELETE)
                   .build();
    }

    private <R extends IntRestComponent> R parseResponse(JiraResponse jiraResponse, Type type) throws IntegrationException {
        String content = jiraResponse.getContent();
        return jsonTransformer.getComponentAs(content, type);
    }

}
