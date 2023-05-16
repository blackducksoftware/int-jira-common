/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
import com.synopsys.integration.rest.component.IntRestResponse;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class JiraApiClient {
    // TODO may want to replace all references to gson with jsonTransformer
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String MEDIA_TYPE = "application/json";
    private final Gson gson;
    private final JiraHttpClient httpClient;
    private final IntJsonTransformer jsonTransformer;

    public JiraApiClient(Gson gson, JiraHttpClient httpClient, IntJsonTransformer jsonTransformer) {
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
        return jsonTransformer.getComponentAs(content, responseClass);
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
            .addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE)
            .bodyContent(bodyContent)
            .build();
    }

    private JiraRequest createPutRequest(HttpUrl url, String bodyContent) {
        return createRequestBuilder(url, HttpMethod.PUT)
            .addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE)
            .bodyContent(bodyContent)
            .build();
    }

    private JiraRequest createDeleteRequest(HttpUrl url) {
        return createRequestBuilder(url, HttpMethod.DELETE)
            .addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE)
            .build();
    }

    private <R extends IntRestComponent> R parseResponse(JiraResponse jiraResponse, Type type) throws IntegrationException {
        String content = jiraResponse.getContent();
        R parsedResponse = jsonTransformer.getComponentAs(content, type);
        if (parsedResponse instanceof IntRestResponse) {
            // Necessary because we don't call jsonTransformer.getResponse(...)
            ((IntRestResponse) parsedResponse).setGson(gson);
        }
        return parsedResponse;
    }

}
