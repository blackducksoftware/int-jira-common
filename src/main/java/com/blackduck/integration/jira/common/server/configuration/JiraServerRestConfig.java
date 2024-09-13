/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.configuration;

import java.net.URL;
import java.util.Optional;
import java.util.function.BiConsumer;

import javax.net.ssl.SSLContext;

import com.blackduck.integration.builder.Buildable;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.server.service.JiraServerServiceFactory;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.blackduck.integration.util.Stringable;
import com.google.gson.Gson;

public abstract class JiraServerRestConfig extends Stringable implements Buildable {
    private final URL jiraUrl;
    private final int timeoutSeconds;
    private final ProxyInfo proxyInfo;
    private final boolean alwaysTrustServerCertificate;
    private final SSLContext sslContext;
    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;

    protected JiraServerRestConfig(
        URL jiraUrl,
        int timeoutSeconds,
        ProxyInfo proxyInfo,
        boolean alwaysTrustServerCertificate,
        Gson gson,
        AuthenticationSupport authenticationSupport
    ) {
        this.jiraUrl = jiraUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.proxyInfo = proxyInfo;
        this.alwaysTrustServerCertificate = alwaysTrustServerCertificate;
        this.sslContext = null;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
    }

    protected JiraServerRestConfig(
            URL jiraUrl,
            int timeoutSeconds,
            ProxyInfo proxyInfo,
            SSLContext sslContext,
            Gson gson,
            AuthenticationSupport authenticationSupport
    ) {
        this.jiraUrl = jiraUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.proxyInfo = proxyInfo;
        this.alwaysTrustServerCertificate = false;
        this.sslContext = sslContext;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
    }

    public abstract JiraHttpClient createJiraHttpClient(IntLogger logger);

    public JiraServerServiceFactory createJiraServerServiceFactory(IntLogger logger) {
        return new JiraServerServiceFactory(logger, createJiraHttpClient(logger), gson);
    }

    public abstract void populateEnvironmentVariables(BiConsumer<String, String> pairsConsumer);

    public URL getJiraUrl() {
        return jiraUrl;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public ProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    public boolean isAlwaysTrustServerCertificate() {
        return alwaysTrustServerCertificate;
    }

    public Optional<SSLContext> getSslContext() {
        return Optional.ofNullable(sslContext);
    }

    public Gson getGson() {
        return gson;
    }

    public AuthenticationSupport getAuthenticationSupport() {
        return authenticationSupport;
    }

}
