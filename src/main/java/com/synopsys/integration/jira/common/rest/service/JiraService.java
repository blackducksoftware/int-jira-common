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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.JiraResponse;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.request.JiraRequestModel;
import com.synopsys.integration.jira.common.rest.JiraCloudPageRequestHandler;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.response.Response;
import com.synopsys.integration.rest.service.IntJsonTransformer;
import com.synopsys.integration.rest.service.IntResponseTransformer;

public class JiraService {
    private final Gson gson;
    private final JiraHttpClient httpClient;
    private final IntResponseTransformer responseTransformer;
    private final IntJsonTransformer jsonTransformer;

    public JiraService(Gson gson, JiraHttpClient httpClient, IntResponseTransformer responseTransformer, IntJsonTransformer jsonTransformer) {
        this.gson = gson;
        this.httpClient = httpClient;
        this.responseTransformer = responseTransformer;
        this.jsonTransformer = jsonTransformer;
    }

    public String getBaseUrl() {
        return httpClient.getBaseUrl();
    }

    public <R extends JiraResponse> R get(Request request, Class<R> responseClass) throws IntegrationException {
        return execute(request, responseClass);
    }

    public Response get(Request request) throws IntegrationException {
        return execute(request);
    }

    public <R extends JiraResponse> List<R> getList(Request request, Class<R> responseClass) throws IntegrationException {
        try (Response response = httpClient.execute(request)) {
            response.throwExceptionForError();
            String responseJson = response.getContentString();

            JsonArray arrayResponse = gson.fromJson(responseJson, JsonArray.class);
            List<R> responseList = new LinkedList<>();
            for (JsonElement jsonElement : arrayResponse) {
                if (jsonElement.isJsonObject()) {
                    R responseElement = jsonTransformer.getComponentAs(jsonElement.getAsJsonObject(), responseClass);
                    responseList.add(responseElement);
                }
            }
            return responseList;
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage(), e);
        }
    }

    public <R extends JiraPageResponseModel> R getAll(Request.Builder requestBuilder, JiraCloudPageRequestHandler pageRequestHandler, Class<R> responseClass) throws IntegrationException {
        return getAll(requestBuilder, pageRequestHandler, responseClass, JiraCloudRequestFactory.DEFAULT_LIMIT);
    }

    public <R extends JiraPageResponseModel> R getAll(Request.Builder requestBuilder, JiraCloudPageRequestHandler pageRequestHandler, Class<R> responseClass, int pageSize) throws IntegrationException {
        return responseTransformer.getResponses(requestBuilder, pageRequestHandler, responseClass, pageSize);
    }

    public <R extends JiraResponse> R post(JiraRequestModel jiraRequestModel, HttpUrl url, Class<R> responseClass) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        Request request = JiraCloudRequestFactory
                              .createCommonPostRequestBuilder(jsonRequestBody)
                              .url(url)
                              .build();
        return execute(request, responseClass);
    }

    public Response post(JiraRequestModel jiraRequestModel, HttpUrl url) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        Request request = JiraCloudRequestFactory
                              .createCommonPostRequestBuilder(jsonRequestBody)
                              .url(url)
                              .build();
        return execute(request);
    }

    public <R extends JiraResponse> R put(JiraRequestModel jiraRequestModel, HttpUrl url, Class<R> responseClass) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        Request request = JiraCloudRequestFactory
                              .createCommonPutRequestBuilder(jsonRequestBody)
                              .url(url)
                              .build();
        return execute(request, responseClass);
    }

    public Response put(JiraRequestModel jiraRequestModel, HttpUrl url) throws IntegrationException {
        String jsonRequestBody = gson.toJson(jiraRequestModel);
        return put(jsonRequestBody, url);
    }

    public Response put(String jsonRequestBody, HttpUrl url) throws IntegrationException {
        Request request = JiraCloudRequestFactory
                              .createCommonPutRequestBuilder(jsonRequestBody)
                              .url(url)
                              .build();
        return execute(request);
    }

    public Response delete(HttpUrl url) throws IntegrationException {
        Request request = JiraCloudRequestFactory
                              .createCommonDeleteRequestBuilder()
                              .url(url)
                              .build();
        return execute(request);
    }

    public <R extends JiraResponse> R delete(HttpUrl url, Class<R> responseClass) throws IntegrationException {
        Request request = JiraCloudRequestFactory
                              .createCommonDeleteRequestBuilder()
                              .url(url)
                              .build();
        return execute(request, responseClass);
    }

    private <R extends JiraResponse> R execute(Request request, Class<R> responseClass) throws IntegrationException {
        return responseTransformer.getResponse(request, responseClass);
    }

    private Response execute(Request request) throws IntegrationException {
        return httpClient.execute(request);
    }

}
