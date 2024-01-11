/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.configuration;

import java.net.URL;
import java.util.Optional;

import com.google.gson.Gson;
import com.synopsys.integration.builder.Buildable;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.rest.JiraCredentialHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;
import com.synopsys.integration.util.Stringable;

import javax.net.ssl.SSLContext;

public class JiraCloudRestConfig extends Stringable implements Buildable {
    private final URL jiraUrl;
    private final int timeoutSeconds;
    private final String authUserEmail;
    private final String apiToken;
    private final ProxyInfo proxyInfo;
    private final boolean alwaysTrustServerCertificate;
    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;
    private final SSLContext sslContext;

    public JiraCloudRestConfig(URL jiraUrl, int timeoutSeconds, ProxyInfo proxyInfo, boolean alwaysTrustServerCertificate, Gson gson, AuthenticationSupport authenticationSupport, String authUserEmail, String apiToken) {
        this(jiraUrl, timeoutSeconds, proxyInfo, alwaysTrustServerCertificate, gson, authenticationSupport, authUserEmail, apiToken, null);
    }

    public JiraCloudRestConfig(URL jiraUrl, int timeoutSeconds, ProxyInfo proxyInfo, SSLContext sslContext, Gson gson, AuthenticationSupport authenticationSupport, String authUserEmail, String apiToken) {
        this(jiraUrl, timeoutSeconds, proxyInfo, false, gson, authenticationSupport, authUserEmail, apiToken, sslContext);
    }

    private JiraCloudRestConfig(URL jiraUrl, int timeoutSeconds, ProxyInfo proxyInfo, boolean alwaysTrustServerCertificate, Gson gson, AuthenticationSupport authenticationSupport, String authUserEmail, String apiToken, SSLContext sslContext) {
        this.jiraUrl = jiraUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.authUserEmail = authUserEmail;
        this.apiToken = apiToken;
        this.proxyInfo = proxyInfo;
        this.alwaysTrustServerCertificate = alwaysTrustServerCertificate;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
        this.sslContext = sslContext;
    }

    public static JiraCloudRestConfigBuilder newBuilder() {
        return new JiraCloudRestConfigBuilder();
    }

    public JiraHttpClient createJiraHttpClient(IntLogger logger) {
        if (getSslContext().isPresent()){
            return new JiraCredentialHttpClient(logger, gson, timeoutSeconds, proxyInfo, getSslContext().get(), jiraUrl.toString(), authenticationSupport, authUserEmail, apiToken);
        } else {
            return new JiraCredentialHttpClient(logger, gson, timeoutSeconds, alwaysTrustServerCertificate, proxyInfo, jiraUrl.toString(), authenticationSupport, authUserEmail, apiToken);
        }
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

    public Optional<SSLContext> getSslContext() {
        return Optional.ofNullable(sslContext);
    }

}
