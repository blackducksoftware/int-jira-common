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
package com.synopsys.integration.jira.common.rest.oauth.http;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;

public class JiraHttpService {
    private final String baseUrl;
    private final HttpRequestFactory httpRequestFactory;
    private final Gson gson;

    public JiraHttpService(String baseUrl, HttpRequestFactory httpRequestFactory, Gson gson) {
        this.baseUrl = baseUrl;
        this.httpRequestFactory = httpRequestFactory;
        this.gson = gson;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpResponse getResponse(String urlEndpoint) throws IOException {
        GenericUrl url = new GenericUrl(baseUrl + urlEndpoint);
        HttpRequest request = httpRequestFactory.buildGetRequest(url);
        return request.execute();
    }

    public <T> T get(String urlEndpoint, Type responseType) throws IntegrationException {
        HttpResponse httpResponse = null;
        try {
            httpResponse = getResponse(urlEndpoint);
            if (!httpResponse.isSuccessStatusCode()) {
                throw new IntegrationException(httpResponse.getStatusMessage());
            }
            return parseResponse(httpResponse, responseType);
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage());
        } finally {
            disconnectResponse(httpResponse);
        }
    }

    public HttpResponse post(String urlEndpoint, Object requestBodyObject) throws IOException {
        GenericUrl url = new GenericUrl(baseUrl + urlEndpoint);
        HttpRequest postRequest = buildPostRequest(url, requestBodyObject);
        return postRequest.execute();
    }

    public <T> T post(String urlEndpoint, Object requestBodyObject, Class<T> responseClass) throws IntegrationException {
        HttpResponse httpResponse = null;
        try {
            httpResponse = post(urlEndpoint, requestBodyObject);
            if (!httpResponse.isSuccessStatusCode()) {
                throw new IntegrationException(httpResponse.getStatusMessage());
            }
            return parseResponse(httpResponse, responseClass);
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage());
        } finally {
            disconnectResponse(httpResponse);
        }
    }

    public HttpResponse put(String urlEndpoint, Object requestBodyObject) throws IOException {
        GenericUrl url = new GenericUrl(baseUrl + urlEndpoint);
        HttpRequest postRequest = buildPutRequest(url, requestBodyObject);
        return postRequest.execute();
    }

    public <T> T put(String urlEndpoint, Object requestBodyObject, Class<T> responseClass) throws IntegrationException {
        HttpResponse httpResponse = null;
        try {
            httpResponse = post(urlEndpoint, requestBodyObject);
            if (!httpResponse.isSuccessStatusCode()) {
                throw new IntegrationException(httpResponse.getStatusMessage());
            }
            return parseResponse(httpResponse, responseClass);
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage());
        } finally {
            disconnectResponse(httpResponse);
        }
    }

    public HttpResponse delete(String urlEndpoint) throws IntegrationException {
        GenericUrl url = new GenericUrl(baseUrl + urlEndpoint);
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestFactory.buildDeleteRequest(url);
            return httpRequest.execute();
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage());
        }
    }

    protected HttpRequest buildPutRequest(GenericUrl url, Object requestBodyObject) throws IOException {
        HttpContent requestContent = buildPostRequestContent(requestBodyObject);
        HttpRequest putRequest = httpRequestFactory.buildPutRequest(url, requestContent);
        return putRequest;
    }

    protected HttpRequest buildPostRequest(GenericUrl url, Object requestBodyObject) throws IOException {
        HttpContent requestContent = buildPostRequestContent(requestBodyObject);
        HttpRequest postRequest = httpRequestFactory.buildPostRequest(url, requestContent);
        return postRequest;
    }

    protected HttpContent buildPostRequestContent(Object requestBodyObject) {
        String objectJson = gson.toJson(requestBodyObject);
        return ByteArrayContent.fromString(null, objectJson);
    }

    protected void disconnectResponse(HttpResponse response) throws IntegrationException {
        if (response == null) {
            return;
        }
        try {
            response.disconnect();
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage());
        }
    }

    protected <T> T parseResponse(HttpResponse response, Type responseType) throws IOException {
        String responseString = response.parseAsString();
        return gson.fromJson(responseString, responseType);
    }
}
