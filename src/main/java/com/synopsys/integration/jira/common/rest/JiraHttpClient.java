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
package com.synopsys.integration.jira.common.rest;

import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.client.BasicAuthHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraHttpClient extends BasicAuthHttpClient {
    private final String baseUrl;

    public static final JiraHttpClient cloud(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String authUserEmail, String apiToken) {
        return new JiraHttpClient(logger, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, new AuthenticationSupport(), authUserEmail, apiToken);
    }

    public static final JiraHttpClient server(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String username, String password) {
        return new JiraHttpClient(logger, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, new AuthenticationSupport(), username, password);
    }

    public JiraHttpClient(IntLogger logger, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, AuthenticationSupport authenticationSupport, String username, String password) {
        super(logger, timeout, alwaysTrustServerCertificate, proxyInfo, authenticationSupport, username, password);
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
