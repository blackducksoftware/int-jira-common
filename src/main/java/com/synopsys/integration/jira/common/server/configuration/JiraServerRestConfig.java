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
package com.synopsys.integration.jira.common.server.configuration;

import java.net.URL;
import java.util.function.BiConsumer;

import com.google.gson.Gson;
import com.synopsys.integration.builder.Buildable;
import com.synopsys.integration.jira.common.rest.JiraCredentialHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;
import com.synopsys.integration.util.Stringable;

public class JiraServerRestConfig extends Stringable implements Buildable {
    private final URL jiraUrl;
    private final int timeoutSeconds;
    private final String authUsername;
    private final String authPassword;
    private final ProxyInfo proxyInfo;
    private final boolean alwaysTrustServerCertificate;
    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;

    public JiraServerRestConfig(URL jiraUrl, int timeoutSeconds, ProxyInfo proxyInfo, boolean alwaysTrustServerCertificate, Gson gson, AuthenticationSupport authenticationSupport, String authUsername, String authPassword) {
        this.jiraUrl = jiraUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.authUsername = authUsername;
        this.authPassword = authPassword;
        this.proxyInfo = proxyInfo;
        this.alwaysTrustServerCertificate = alwaysTrustServerCertificate;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
    }

    public static final JiraServerRestConfigBuilder newBuilder() {
        return new JiraServerRestConfigBuilder();
    }

    public JiraHttpClient createJiraHttpClient(IntLogger logger) {
        return new JiraCredentialHttpClient(logger, timeoutSeconds, alwaysTrustServerCertificate, proxyInfo, jiraUrl.toString(), authenticationSupport, authUsername, authPassword);
    }

    public JiraServerServiceFactory createJiraServerServiceFactory(IntLogger logger) {
        return new JiraServerServiceFactory(logger, createJiraHttpClient(logger), gson);
    }

    public void populateEnvironmentVariables(BiConsumer<String, String> pairsConsumer) {
        pairsConsumer.accept(JiraServerRestConfigBuilder.URL_KEY.getKey(), jiraUrl.toString());
        pairsConsumer.accept(JiraServerRestConfigBuilder.AUTH_USERNAME.getKey(), authUsername);
        pairsConsumer.accept(JiraServerRestConfigBuilder.AUTH_PASSWORD.getKey(), authPassword);
    }

    public URL getJiraUrl() {
        return jiraUrl;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public String getAuthUsername() {
        return authUsername;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public ProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    public boolean isAlwaysTrustServerCertificate() {
        return alwaysTrustServerCertificate;
    }

    public Gson getGson() {
        return gson;
    }

    public AuthenticationSupport getAuthenticationSupport() {
        return authenticationSupport;
    }

}
