/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.oauth1a.JiraOAuthHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;

public class JiraHttpClientFactory {
    public static final String JIRA_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    public JiraHttpClient createJiraOAuthClient(String jiraUrl, OAuthParameters oAuthParameters) {
        return new JiraOAuthHttpClient(jiraUrl, createHttpRequestFactory(oAuthParameters));
    }

    public JiraHttpClient createJiraCloudCredentialClient(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String authUserEmail, String apiToken) {
        return JiraCredentialHttpClient.cloud(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, authUserEmail, apiToken);
    }

    public JiraHttpClient createJiraServerCredentialClient(IntLogger logger, Gson gson, int timeout, boolean alwaysTrustServerCertificate, ProxyInfo proxyInfo, String baseUrl, String username, String password) {
        return JiraCredentialHttpClient.server(logger, gson, timeout, alwaysTrustServerCertificate, proxyInfo, baseUrl, username, password);
    }

    private HttpRequestFactory createHttpRequestFactory(OAuthParameters oAuthParameters) {
        return new NetHttpTransport().createRequestFactory(oAuthParameters);
    }

}

