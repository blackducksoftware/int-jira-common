/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest;

import javax.net.ssl.SSLContext;

import com.blackduck.integration.jira.common.rest.oauth1a.JiraOAuthHttpClient;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;

public class JiraHttpClientFactory {
    public static final String JIRA_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    public JiraHttpClient createJiraOAuthClient(String jiraUrl, OAuthParameters oAuthParameters) {
        return new JiraOAuthHttpClient(jiraUrl, createHttpRequestFactory(oAuthParameters));
    }

    public JiraHttpClient createJiraOAuthClient(String jiraUrl, OAuthParameters oAuthParameters, SSLContext sslContext) {
        return new JiraOAuthHttpClient(jiraUrl, createHttpRequestFactory(oAuthParameters, sslContext));
    }

    public JiraHttpClient createJiraCloudCredentialClient(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String authUserEmail, String apiToken) {
        return JiraCredentialHttpClient.cloud(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, authUserEmail, apiToken);
    }

    public JiraHttpClient createJiraCloudCredentialClient(IntLogger logger, Gson gson, int timeout, ProxyInfo proxyInfo, SSLContext sslContext, String baseUrl, String authUserEmail, String apiToken) {
        return JiraCredentialHttpClient.cloud(logger, gson, timeout, proxyInfo, sslContext, baseUrl, authUserEmail, apiToken);
    }

    public JiraHttpClient createJiraServerCredentialClient(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String username, String password) {
        return JiraCredentialHttpClient.server(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, username, password);
    }

    public JiraHttpClient createJiraServerCredentialClient(IntLogger logger, Gson gson, int timeout, ProxyInfo proxyInfo, SSLContext sslContext, String baseUrl, String username, String password) {
        return JiraCredentialHttpClient.server(logger, gson, timeout, proxyInfo, sslContext, baseUrl, username, password);
    }

    private HttpRequestFactory createHttpRequestFactory(OAuthParameters oAuthParameters) {
        return new NetHttpTransport().createRequestFactory(oAuthParameters);
    }

    private HttpRequestFactory createHttpRequestFactory(OAuthParameters oAuthParameters, SSLContext sslContext) {
        return new NetHttpTransport
                .Builder()
                .setSslSocketFactory(sslContext.getSocketFactory())
                .build()
                .createRequestFactory(oAuthParameters);
    }
}

