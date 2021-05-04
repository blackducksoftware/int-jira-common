/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
    public static final String LIMIT_PARAMETER = "maxResults";
    public static final String OFFSET_PARAMETER = "startAt";
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

    private JiraRequestFactory() {
        // empty private constructor for utility class to prevent users from creating an instance of this class
    }

}
