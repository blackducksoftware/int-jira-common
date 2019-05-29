package com.synopsys.integration.jira.common.cloud.configuration;

import java.net.URL;
import java.util.function.BiConsumer;

import com.google.gson.Gson;
import com.synopsys.integration.builder.Buildable;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;
import com.synopsys.integration.util.Stringable;

public class JiraServerConfig extends Stringable implements Buildable {
    private final URL jiraUrl;
    private final int timeoutSeconds;
    private final String userName;
    private final String accessToken;
    private final ProxyInfo proxyInfo;
    private final boolean alwaysTrustServerCertificate;
    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;

    public JiraServerConfig(URL jiraUrl, int timeoutSeconds, String userName, String accessToken, ProxyInfo proxyInfo, boolean alwaysTrustServerCertificate, Gson gson,
        AuthenticationSupport authenticationSupport) {
        this.jiraUrl = jiraUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.userName = userName;
        this.accessToken = accessToken;
        this.proxyInfo = proxyInfo;
        this.alwaysTrustServerCertificate = alwaysTrustServerCertificate;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
    }

    public static final JiraServerConfigBuilder newBuilder() { return new JiraServerConfigBuilder(); }

    //    public AccessTokenPolarisHttpClient createPolarisHttpClient(IntLogger logger) {
    //        return new AccessTokenPolarisHttpClient(logger, timeoutSeconds, alwaysTrustServerCertificate, proxyInfo, jiraUrl.toString(), accessToken, gson, authenticationSupport);
    //    }
    //
    //    public PolarisServicesFactory createPolarisServicesFactory(IntLogger logger) {
    //        return new PolarisServicesFactory(logger, createPolarisHttpClient(logger), gson);
    //    }
    //
    public void populateEnvironmentVariables(BiConsumer<String, String> pairsConsumer) {
        pairsConsumer.accept(JiraServerConfigBuilder.URL_KEY.getKey(), jiraUrl.toString());
        pairsConsumer.accept(JiraServerConfigBuilder.USERNAME_KEY.getKey(), userName);
        pairsConsumer.accept(JiraServerConfigBuilder.ACCESS_TOKEN_KEY.getKey(), accessToken);
    }

    public URL getJiraUrl() {
        return jiraUrl;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccessToken() {
        return accessToken;
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
