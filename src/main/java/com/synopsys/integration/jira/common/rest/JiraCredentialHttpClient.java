/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.body.BodyContentConverter;
import com.synopsys.integration.rest.body.StringBodyContent;
import com.synopsys.integration.rest.client.BasicAuthHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.response.Response;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraCredentialHttpClient extends BasicAuthHttpClient implements JiraHttpClient {
    private final String baseUrl;

    public static final JiraHttpClient cloud(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String authUserEmail, String apiToken) {
        return new JiraCredentialHttpClient(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, new AuthenticationSupport(), authUserEmail, apiToken);
    }

    public static final JiraHttpClient server(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String username, String password) {
        return new JiraCredentialHttpClient(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, new AuthenticationSupport(), username, password);
    }

    public JiraCredentialHttpClient(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, AuthenticationSupport authenticationSupport, String username, String password) {
        super(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, authenticationSupport, username, password);
        this.baseUrl = baseUrl;
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
        completeHeaders.put(HttpHeaders.ACCEPT, jiraRequest.getAcceptMimeType());

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

}
