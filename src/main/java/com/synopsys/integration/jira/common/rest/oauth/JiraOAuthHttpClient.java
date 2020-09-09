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
package com.synopsys.integration.jira.common.rest.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Charsets;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.function.ThrowingFunction;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;

public class JiraOAuthHttpClient implements JiraHttpClient {
    private final String baseUrl;
    private final HttpRequestFactory httpRequestFactory;

    public JiraOAuthHttpClient(String baseUrl, HttpRequestFactory httpRequestFactory) {
        this.baseUrl = baseUrl;
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public JiraResponse execute(JiraRequest jiraRequest) throws IntegrationException {
        return executeRequestAndProcess(jiraRequest, this::convertToJiraResponse);
    }

    private Map<String, String> convertHttpHeaderToMap(HttpResponse httpResponse) {
        HttpHeaders headers = httpResponse.getHeaders();
        return headers.keySet().stream().collect(Collectors.toMap(Function.identity(), headers::getFirstHeaderStringValue));
    }

    private <R> R executeRequestAndProcess(JiraRequest jiraRequest, ThrowingFunction<HttpResponse, R, IOException> returnResponse) throws IntegrationException {
        HttpResponse httpResponse = null;
        try {
            HttpRequest httpRequest = convertToRequest(jiraRequest);
            httpResponse = httpRequest.execute();
            if (!httpResponse.isSuccessStatusCode()) {
                throw new IntegrationException(httpResponse.getStatusMessage());
            }
            return returnResponse.apply(httpResponse);
        } catch (IOException | URISyntaxException e) {
            throw new IntegrationException(e.getMessage());
        } finally {
            disconnectResponse(httpResponse);
        }
    }

    private HttpRequest convertToRequest(JiraRequest jiraRequest) throws IOException, URISyntaxException {
        GenericUrl genericUrl = createGenericUrl(jiraRequest.getUrl().string(), jiraRequest.getPopulatedQueryParameters());
        HttpContent body = null;
        if (StringUtils.isNotBlank(jiraRequest.getBodyContent())) {
            body = ByteArrayContent.fromString(null, jiraRequest.getBodyContent());
        }
        HttpRequest httpRequest = httpRequestFactory.buildRequest(
            jiraRequest.getMethod().name(),
            genericUrl,
            body
        );
        HttpHeaders httpHeaders = convertHeadersToHttpHeaders(jiraRequest.getHeaders());
        if (StringUtils.isBlank(httpHeaders.getAccept())) {
            httpHeaders.setAccept(jiraRequest.getAcceptMimeType());
        }
        httpRequest.setHeaders(httpHeaders);

        return httpRequest;
    }

    private GenericUrl createGenericUrl(String url, Map<String, Set<String>> queryParams) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        queryParams.entrySet()
            .stream()
            .forEach(entry ->
                         uriBuilder.addParameter(entry.getKey(), entry.getValue()
                                                                     .stream()
                                                                     .collect(Collectors.joining(",")))
            );
        return new GenericUrl(uriBuilder.build());
    }

    private HttpHeaders convertHeadersToHttpHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.entrySet().forEach(entry -> httpHeaders.set(entry.getKey(), entry.getValue()));
        return httpHeaders;
    }

    private JiraResponse convertToJiraResponse(HttpResponse httpResponse) throws IOException {
        String content = buildResponseBody(httpResponse.getContent());
        Map<String, String> headers = convertHttpHeaderToMap(httpResponse);
        return new JiraResponse(httpResponse.getStatusCode(), httpResponse.getStatusMessage(), content, headers);
    }

    private String buildResponseBody(InputStream responseStream) throws IOException {
        return IOUtils.toString(responseStream, Charsets.UTF_8);
    }

    private void disconnectResponse(HttpResponse response) throws IntegrationException {
        if (response == null) {
            return;
        }
        try {
            response.disconnect();
        } catch (IOException e) {
            throw new IntegrationException(e.getMessage());
        }
    }
}
