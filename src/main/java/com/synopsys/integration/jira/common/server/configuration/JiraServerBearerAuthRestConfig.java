/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.server.configuration;

import java.net.URL;
import java.util.function.BiConsumer;

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.token.JiraAccessTokenHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;

import javax.net.ssl.SSLContext;

public class JiraServerBearerAuthRestConfig extends JiraServerRestConfig {
    private final String accessToken;

    protected JiraServerBearerAuthRestConfig(
        URL jiraUrl,
        int timeoutSeconds,
        ProxyInfo proxyInfo,
        boolean alwaysTrustServerCertificate,
        Gson gson,
        AuthenticationSupport authenticationSupport,
        String accessToken
    ) {
        super(jiraUrl, timeoutSeconds, proxyInfo, alwaysTrustServerCertificate, gson, authenticationSupport);
        this.accessToken = accessToken;
    }

    protected JiraServerBearerAuthRestConfig(
            URL jiraUrl,
            int timeoutSeconds,
            ProxyInfo proxyInfo,
            SSLContext sslContext,
            Gson gson,
            AuthenticationSupport authenticationSupport,
            String accessToken
    ) {
        super(jiraUrl, timeoutSeconds, proxyInfo, sslContext, gson, authenticationSupport);
        this.accessToken = accessToken;
    }

    @Override
    public JiraHttpClient createJiraHttpClient(IntLogger logger) {
        if (getSslContext().isPresent()) {
            return new JiraAccessTokenHttpClient(
                    logger,
                    getGson(),
                    getTimeoutSeconds(),
                    getProxyInfo(),
                    getSslContext().get(),
                    getJiraUrl().toString(),
                    getAuthenticationSupport(),
                    accessToken
            );
        } else {
            return new JiraAccessTokenHttpClient(
                    logger,
                    getGson(),
                    getTimeoutSeconds(),
                    isAlwaysTrustServerCertificate(),
                    getProxyInfo(),
                    getJiraUrl().toString(),
                    getAuthenticationSupport(),
                    accessToken
            );
        }

    }

    @Override
    public void populateEnvironmentVariables(BiConsumer<String, String> pairsConsumer) {
        pairsConsumer.accept(JiraServerRestConfigBuilder.URL_KEY.getKey(), getJiraUrl().toString());
        pairsConsumer.accept(JiraServerBearerAuthRestConfigBuilder.AUTH_PERSONAL_ACCESS_TOKEN.getKey(), accessToken);
    }
}
