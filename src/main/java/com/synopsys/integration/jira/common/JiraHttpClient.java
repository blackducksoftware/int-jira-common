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
package com.synopsys.integration.jira.common;

import java.util.Base64;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.RestConstants;
import com.synopsys.integration.rest.client.AuthenticatingIntHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.request.Response;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraHttpClient extends AuthenticatingIntHttpClient {
    private static final String AUTHORIZATION_TYPE = "Basic";

    private final AuthenticationSupport authenticationSupport;
    private final String baseUrl;
    private final String userEmail;
    private final String apiToken;

    public JiraHttpClient(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, AuthenticationSupport authenticationSupport, String authUserEmail, String apiToken) {
        super(logger, timeout, alwaysTrustServerCertificate, proxyInfo);
        this.authenticationSupport = authenticationSupport;
        this.baseUrl = baseUrl;

        this.userEmail = authUserEmail;
        this.apiToken = apiToken;
    }

    @Override
    public void populateHttpClientBuilder(HttpClientBuilder httpClientBuilder, RequestConfig.Builder defaultRequestConfigBuilder) {
        httpClientBuilder.setDefaultCookieStore(new BasicCookieStore());
        defaultRequestConfigBuilder.setCookieSpec(CookieSpecs.DEFAULT);
    }

    @Override
    public void handleErrorResponse(HttpUriRequest request, Response response) {
        super.handleErrorResponse(request, response);

        authenticationSupport.handleErrorResponse(this, request, response, RestConstants.X_CSRF_TOKEN);
    }

    @Override
    public boolean isAlreadyAuthenticated(HttpUriRequest request) {
        return request.containsHeader(AuthenticationSupport.AUTHORIZATION_HEADER);
    }

    @Override
    protected void completeAuthenticationRequest(HttpUriRequest request, Response responseIgnored) {
        final Base64.Encoder encoder = Base64.getEncoder();
        final String unencodedAuthPair = String.format("%s:%s", userEmail, apiToken);
        final String encodedAuthPair = encoder.encodeToString(unencodedAuthPair.getBytes());
        final String encodedHeaderValue = String.format("%s %s", AUTHORIZATION_TYPE, encodedAuthPair);

        authenticationSupport.addAuthenticationHeader(this, request, AuthenticationSupport.AUTHORIZATION_HEADER, encodedHeaderValue);
    }

    @Override
    public final Response attemptAuthentication() throws IntegrationException {
        // Nothing to do because only Basic Auth is used.
        return null;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
