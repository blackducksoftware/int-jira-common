/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.configuration;

import java.net.URL;

import com.google.gson.Gson;
import com.synopsys.integration.builder.Buildable;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.rest.JiraCredentialHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;
import com.synopsys.integration.util.Stringable;

public class JiraCloudRestConfig extends Stringable implements Buildable {
    private final URL jiraUrl;
    private final int timeoutSeconds;
    private final String authUserEmail;
    private final String apiToken;
    private final ProxyInfo proxyInfo;
    private final boolean alwaysTrustServerCertificate;
    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;

    public JiraCloudRestConfig(URL jiraUrl, int timeoutSeconds, ProxyInfo proxyInfo, boolean alwaysTrustServerCertificate, Gson gson, AuthenticationSupport authenticationSupport, String authUserEmail, String apiToken) {
        this.jiraUrl = jiraUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.authUserEmail = authUserEmail;
        this.apiToken = apiToken;
        this.proxyInfo = proxyInfo;
        this.alwaysTrustServerCertificate = alwaysTrustServerCertificate;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
    }

    public static final JiraCloudRestConfigBuilder newBuilder() {
        return new JiraCloudRestConfigBuilder();
    }

    public JiraHttpClient createJiraHttpClient(IntLogger logger) {
        return new JiraCredentialHttpClient(logger, timeoutSeconds, alwaysTrustServerCertificate, proxyInfo, jiraUrl.toString(), authenticationSupport, authUserEmail, apiToken);
    }

    public JiraCloudServiceFactory createJiraCloudServiceFactory(IntLogger logger) {
        return new JiraCloudServiceFactory(logger, createJiraHttpClient(logger), gson);
    }

    public URL getJiraUrl() {
        return jiraUrl;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public String getAuthUserEmail() {
        return authUserEmail;
    }

    public String getApiToken() {
        return apiToken;
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
