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

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.rest.JiraCloudHttpClient;
import com.synopsys.integration.jira.common.cloud.rest.JiraCloudPageRequestHandler;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.JiraResponse;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.service.IntResponseTransformer;

public class JiraCloudService {
    public static final int DEFAULT_PAGE_SIZE = 50;

    private final Gson gson;
    private final JiraCloudHttpClient httpClient;
    private final IntResponseTransformer responseTransformer;

    // TODO implement methods as needed
    public JiraCloudService(final Gson gson, final JiraCloudHttpClient httpClient, final IntResponseTransformer responseTransformer) {
        this.gson = gson;
        this.httpClient = httpClient;
        this.responseTransformer = responseTransformer;
    }

    public String getBaseUrl() {
        return httpClient.getBaseUrl();
    }

    public <R extends JiraResponse> R get(Request request, Class<R> responseClass) throws IntegrationException {
        return responseTransformer.getResponse(request, responseClass);
    }

    public <R extends JiraPageResponseModel> R getAll(Request.Builder requestBuilder, JiraCloudPageRequestHandler pageRequestHandler, Class<R> responseClass) throws IntegrationException {
        return getAll(requestBuilder, pageRequestHandler, responseClass, DEFAULT_PAGE_SIZE);
    }

    public <R extends JiraPageResponseModel> R getAll(Request.Builder requestBuilder, JiraCloudPageRequestHandler pageRequestHandler, Class<R> responseClass, int pageSize) throws IntegrationException {
        return responseTransformer.getResponses(requestBuilder, pageRequestHandler, responseClass, pageSize);
    }

}
