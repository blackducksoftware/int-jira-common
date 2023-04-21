package com.synopsys.integration.jira.common.server.configuration;

import java.net.URL;
import java.util.function.BiConsumer;

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraCredentialHttpClient;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.proxy.ProxyInfo;
import com.synopsys.integration.rest.support.AuthenticationSupport;

public class JiraServerBasicAuthRestConfig extends JiraServerRestConfig {
    private final String authUsername;
    private final String authPassword;

    protected JiraServerBasicAuthRestConfig(
        URL jiraUrl,
        int timeoutSeconds,
        ProxyInfo proxyInfo,
        boolean alwaysTrustServerCertificate,
        Gson gson,
        AuthenticationSupport authenticationSupport,
        String authUsername,
        String authPassword
    ) {
        super(jiraUrl, timeoutSeconds, proxyInfo, alwaysTrustServerCertificate, gson, authenticationSupport);
        this.authUsername = authUsername;
        this.authPassword = authPassword;
    }

    @Override
    public JiraHttpClient createJiraHttpClient(IntLogger logger) {
        return new JiraCredentialHttpClient(
            logger,
            getGson(),
            getTimeoutSeconds(),
            isAlwaysTrustServerCertificate(),
            getProxyInfo(),
            getJiraUrl().toString(),
            getAuthenticationSupport(),
            authUsername,
            authPassword
        );
    }

    @Override
    public void populateEnvironmentVariables(BiConsumer<String, String> pairsConsumer) {
        pairsConsumer.accept(JiraServerRestConfigBuilder.URL_KEY.getKey(), getJiraUrl().toString());
        pairsConsumer.accept(JiraServerBasicAuthRestConfigBuilder.AUTH_USERNAME.getKey(), authUsername);
        pairsConsumer.accept(JiraServerBasicAuthRestConfigBuilder.AUTH_PASSWORD.getKey(), authPassword);
    }
}
