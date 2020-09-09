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
package com.synopsys.integration.jira.common.model.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.HttpUrl;

public class JiraRequestFactory {
    public static final String LIMIT_PARAMETER = "limit";
    public static final String OFFSET_PARAMETER = "offset";
    public static final int DEFAULT_LIMIT = 50;
    public static final int DEFAULT_OFFSET = 0;
    public static final String DEFAULT_MIME_TYPE = "application/json";

    public static final JiraRequest createDefaultGetRequest(HttpUrl requestUrl) {
        return createDefaultBuilder()
                   .url(requestUrl)
                   .build();
    }

    public static final JiraRequest.Builder createDefaultPageRequestBuilder() {
        return populatePageRequestBuilder(createDefaultBuilder(), DEFAULT_LIMIT, DEFAULT_OFFSET);
    }

    public static final JiraRequest.Builder createDefaultPageRequestBuilder(int limit, int offset) {
        return populatePageRequestBuilder(createDefaultBuilder(), limit, offset);
    }

    public static final JiraRequest.Builder populatePageRequestBuilder(JiraRequest.Builder requestBuilder, int limit, int offset) {
        Map<String, Set<String>> queryParameters = requestBuilder.getQueryParameters();
        if (null == queryParameters) {
            requestBuilder.queryParameters(new HashMap<>());
            queryParameters = requestBuilder.getQueryParameters();
        }
        queryParameters.put(LIMIT_PARAMETER, Collections.singleton(Integer.toString(limit)));
        queryParameters.put(OFFSET_PARAMETER, Collections.singleton(Integer.toString(offset)));
        return requestBuilder;
    }

    public static final JiraRequest.Builder createDefaultBuilder() {
        return new JiraRequest.Builder()
                   .acceptMimeType(DEFAULT_MIME_TYPE)
                   .method(HttpMethod.GET);
    }

    public static final JiraRequest.Builder createCommonPostRequestBuilder(String bodyContent) {
        return new JiraRequest.Builder()
                   .method(HttpMethod.POST)
                   .bodyContent(bodyContent);
    }

    public static final JiraRequest.Builder createCommonPutRequestBuilder(String bodyContent) {
        return new JiraRequest.Builder()
                   .method(HttpMethod.PUT)
                   .bodyContent(bodyContent);
    }

    public static final JiraRequest.Builder createCommonDeleteRequestBuilder() {
        return new JiraRequest.Builder()
                   .method(HttpMethod.DELETE);
    }

    private JiraCloudRequestFactory() {
        // empty private constructor for utility class to prevent users from creating an instance of this class
    }

}
