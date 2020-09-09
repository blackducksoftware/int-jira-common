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
package com.synopsys.integration.jira.common.rest;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.body.StringBodyContent;
import com.synopsys.integration.rest.client.BasicAuthHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.response.Response;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraCredentialHttpClient extends BasicAuthHttpClient implements JiraHttpClient {
    private final String baseUrl;

    public static final JiraHttpClient cloud(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String authUserEmail, String apiToken) {
        return new JiraCredentialHttpClient(logger, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, new AuthenticationSupport(), authUserEmail, apiToken);
    }

    public static final JiraHttpClient server(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String username, String password) {
        return new JiraCredentialHttpClient(logger, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, new AuthenticationSupport(), username, password);
    }

    public JiraCredentialHttpClient(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, AuthenticationSupport authenticationSupport, String username, String password) {
        super(logger, timeout, alwaysTrustServerCertificate, proxyInfo, authenticationSupport, username, password);
        this.baseUrl = baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public JiraResponse execute(JiraRequest jiraRequest) throws IntegrationException {
        Request request = convertToRequest(jiraRequest);
        Response response = execute(request);
        response.throwExceptionForError();
        JiraResponse jiraResponse = convertToJiraResponse(response);
        try {
            response.close();
        } catch (IOException e) {
            throw new IntegrationException("Was unable to close response object: " + e.getCause(), e);
        }
        return jiraResponse;
    }

    private Request convertToRequest(JiraRequest jiraRequest) {
        Request.Builder builder = new Request.Builder()
                                      .url(jiraRequest.getUrl())
                                      .method(jiraRequest.getMethod())
                                      .headers(jiraRequest.getHeaders())
                                      .queryParameters(jiraRequest.getQueryParameters())
                                      .acceptMimeType(jiraRequest.getAcceptMimeType());
        if (StringUtils.isNotBlank(jiraRequest.getBodyContent())) {
            builder
                .bodyContent(new StringBodyContent(jiraRequest.getBodyContent()))
                .bodyEncoding(jiraRequest.getBodyEncoding());
        }
        return builder.build();
    }

    private JiraResponse convertToJiraResponse(Response response) throws IntegrationException {
        return new JiraResponse(response.getStatusCode(), response.getStatusMessage(), response.getContentString(), response.getHeaders());
    }

}
