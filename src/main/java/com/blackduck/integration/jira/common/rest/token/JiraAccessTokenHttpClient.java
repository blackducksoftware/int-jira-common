/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.token;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.model.JiraResponse;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.rest.HttpMethod;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.body.BodyContentConverter;
import com.blackduck.integration.rest.body.StringBodyContent;
import com.blackduck.integration.rest.client.AuthenticatingIntHttpClient;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.request.Request;
import com.blackduck.integration.rest.response.Response;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.google.gson.Gson;

public class JiraAccessTokenHttpClient extends AuthenticatingIntHttpClient implements JiraHttpClient {
    private static final String AUTHORIZATION_TYPE = "Bearer";
    private final AuthenticationSupport authenticationSupport;
    private final String baseUrl;
    private final String accessToken;

    public JiraAccessTokenHttpClient(
        IntLogger logger,
        Gson gson,
        int timeout,
        boolean alwaysTrustServerCertificate,
        ProxyInfo proxyInfo,
        String baseUrl,
        AuthenticationSupport authenticationSupport,
        String accessToken
    ) {
        super(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo);
        this.authenticationSupport = authenticationSupport;
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
    }

    public JiraAccessTokenHttpClient(
            IntLogger logger,
            Gson gson,
            int timeout,
            ProxyInfo proxyInfo,
            SSLContext sslContext,
            String baseUrl,
            AuthenticationSupport authenticationSupport,
            String accessToken
    ) {
        super(logger, gson, timeout, proxyInfo, sslContext);
        this.authenticationSupport = authenticationSupport;
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public JiraResponse execute(JiraRequest jiraRequest) throws IntegrationException {
        Request request = convertToRequest(jiraRequest);
        try (Response response = execute(request)) {
            throwExceptionForError(response);
            return convertToJiraResponse(jiraRequest.getMethod(), jiraRequest.getUrl(), response);
        } catch (IOException e) {
            throw new IntegrationException("Was unable to close response object: " + e.getCause(), e);
        }
    }

    private Request convertToRequest(JiraRequest jiraRequest) {
        Map<String, String> completeHeaders = new HashMap<>(jiraRequest.getHeaders());
        if (!completeHeaders.containsKey(HttpHeaders.ACCEPT)) {
            completeHeaders.put(HttpHeaders.ACCEPT, jiraRequest.getAcceptMimeType());
        }

        Request.Builder builder = new Request.Builder()
            .url(jiraRequest.getUrl())
            .method(jiraRequest.getMethod())
            .headers(completeHeaders)
            .queryParameters(jiraRequest.getQueryParameters());
        if (StringUtils.isNotBlank(jiraRequest.getBodyContent())) {
            builder
                .bodyContent(new StringBodyContent(jiraRequest.getBodyContent(), BodyContentConverter.DEFAULT))
                .bodyEncoding(jiraRequest.getBodyEncoding());
        }
        return builder.build();
    }

    private JiraResponse convertToJiraResponse(HttpMethod httpMethod, HttpUrl httpUrl, Response response) throws IntegrationException {
        return new JiraResponse(httpMethod, httpUrl, response.getStatusCode(), response.getStatusMessage(), response.getContentString(), response.getHeaders());
    }

    @Override
    public boolean isAlreadyAuthenticated(HttpUriRequest request) {
        return authenticationSupport.isTokenAlreadyAuthenticated(request);
    }

    @Override
    public Response attemptAuthentication() {
        // Bearer Authorization type is added when creating the request, we can skip this step.
        return null;
    }

    @Override
    protected void completeAuthenticationRequest(HttpUriRequest request, Response response) {
        String headerValues = String.format("%s %s", AUTHORIZATION_TYPE, accessToken);
        authenticationSupport.addAuthenticationHeader(this, request, AuthenticationSupport.AUTHORIZATION_HEADER, headerValues);
    }
}
