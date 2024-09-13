/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.oauth1a;

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

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.model.JiraResponse;
import com.blackduck.integration.rest.HttpMethod;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.exception.IntegrationRestException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.util.Charsets;

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
        return executeRequestAndProcess(jiraRequest);
    }

    private Map<String, String> convertHttpHeaderToMap(HttpResponse httpResponse) {
        HttpHeaders headers = httpResponse.getHeaders();
        return headers.keySet().stream().collect(Collectors.toMap(Function.identity(), headers::getFirstHeaderStringValue));
    }

    private JiraResponse executeRequestAndProcess(JiraRequest jiraRequest) throws IntegrationException {
        HttpResponse httpResponse = null;
        try {
            HttpRequest httpRequest = convertToRequest(jiraRequest);
            httpResponse = httpRequest.execute();
            if (!httpResponse.isSuccessStatusCode()) {
                throw new IntegrationException(httpResponse.getStatusMessage());
            }
            return convertToJiraResponse(jiraRequest.getMethod(), jiraRequest.getUrl(), httpResponse);
        } catch (HttpResponseException e) {
            throw new IntegrationRestException(jiraRequest.getMethod(), jiraRequest.getUrl(), e.getStatusCode(), e.getStatusMessage(), e.getContent(), e);
        } catch (IOException | URISyntaxException e) {
            throw new IntegrationException(e.getMessage(), e);
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
            .filter(entry -> entry.getValue() != null)
            .filter(entry -> entry.getValue()
                                 .stream()
                                 .allMatch(StringUtils::isNotBlank))
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

    private JiraResponse convertToJiraResponse(HttpMethod httpMethod, HttpUrl httpUrl, HttpResponse httpResponse) throws IOException {
        String content = buildResponseBody(httpResponse.getContent());
        Map<String, String> headers = convertHttpHeaderToMap(httpResponse);
        return new JiraResponse(httpMethod, httpUrl, httpResponse.getStatusCode(), httpResponse.getStatusMessage(), content, headers);
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
