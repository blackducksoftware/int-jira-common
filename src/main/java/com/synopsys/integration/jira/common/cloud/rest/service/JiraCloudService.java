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
package com.synopsys.integration.jira.common.cloud.rest.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.cloud.model.request.JiraRequestModel;
import com.synopsys.integration.jira.common.cloud.rest.JiraCloudHttpClient;
import com.synopsys.integration.jira.common.cloud.rest.JiraCloudPageRequestHandler;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.JiraResponse;
import com.synopsys.integration.rest.component.IntRestResponse;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;
import com.synopsys.integration.rest.service.IntJsonTransformer;
import com.synopsys.integration.rest.service.IntResponseTransformer;

public class JiraCloudService {
    private final Gson gson;
    private final JiraCloudHttpClient httpClient;
    private final IntResponseTransformer responseTransformer;
    private final IntJsonTransformer jsonTransformer;

    // TODO implement methods as needed
    public JiraCloudService(final Gson gson, final JiraCloudHttpClient httpClient, final IntResponseTransformer responseTransformer, final IntJsonTransformer jsonTransformer) {
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

    public <R extends JiraResponse> List<R> getList(Request request, Class<R> responseClass) throws IntegrationException {
        try (Response response = httpClient.execute(request)) {
            response.throwExceptionForError();
            final String responseJson = response.getContentString();

            final JsonArray arrayResponse = gson.fromJson(responseJson, JsonArray.class);
            final List<R> responseList = new LinkedList<>();
            for (final JsonElement jsonElement : arrayResponse) {
                if (jsonElement.isJsonObject()) {
                    final R responseElement = jsonTransformer.getComponentAs(jsonElement.getAsJsonObject(), responseClass);
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

    public <R extends JiraResponse> R post(JiraRequestModel jiraRequestModel, String postUri, Class<R> responseClass) throws IntegrationException {
        final String jsonRequestBody = gson.toJson(jiraRequestModel);
        final Request request = JiraCloudRequestFactory
                                    .createCommonPostRequestBuilder(jsonRequestBody)
                                    .uri(postUri)
                                    .build();
        return execute(request, responseClass);
    }

    private <R extends IntRestResponse> R execute(Request request, Class<R> responseClass) throws IntegrationException {
        return responseTransformer.getResponse(request, responseClass);
    }

}
