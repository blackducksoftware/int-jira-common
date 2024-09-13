/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.configuration;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.blackduck.integration.builder.BuilderProperties;
import com.blackduck.integration.builder.BuilderPropertyKey;
import com.blackduck.integration.builder.BuilderStatus;
import com.blackduck.integration.builder.IntegrationBuilder;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;
import com.blackduck.integration.rest.credentials.Credentials;
import com.blackduck.integration.rest.credentials.CredentialsBuilder;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.proxy.ProxyInfoBuilder;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.google.gson.Gson;

public class JiraCloudRestConfigBuilder extends IntegrationBuilder<JiraCloudRestConfig> {
    public static final BuilderPropertyKey URL_KEY = new BuilderPropertyKey("JIRA_URL");
    public static final BuilderPropertyKey AUTH_USER_EMAIL = new BuilderPropertyKey("JIRA_AUTH_USER_EMAIL");
    public static final BuilderPropertyKey ACCESS_TOKEN_KEY = new BuilderPropertyKey("JIRA_API_TOKEN");
    public static final BuilderPropertyKey TRUST_CERT_KEY = new BuilderPropertyKey("JIRA_TRUST_CERT");
    public static final BuilderPropertyKey TIMEOUT_KEY = new BuilderPropertyKey("JIRA_TIMEOUT");
    public static final BuilderPropertyKey PROXY_HOST_KEY = new BuilderPropertyKey("JIRA_PROXY_HOST");
    public static final BuilderPropertyKey PROXY_PORT_KEY = new BuilderPropertyKey("JIRA_PROXY_PORT");
    public static final BuilderPropertyKey PROXY_USERNAME_KEY = new BuilderPropertyKey("JIRA_PROXY_USERNAME");
    public static final BuilderPropertyKey PROXY_PASSWORD_KEY = new BuilderPropertyKey("JIRA_PROXY_PASSWORD");
    public static final BuilderPropertyKey PROXY_NTLM_DOMAIN_KEY = new BuilderPropertyKey("JIRA_PROXY_NTLM_DOMAIN");
    public static final BuilderPropertyKey PROXY_NTLM_WORKSTATION_KEY = new BuilderPropertyKey("JIRA_PROXY_NTLM_WORKSTATION");

    public static final int DEFAULT_TIMEOUT_SECONDS = 120;

    private final BuilderProperties builderProperties;
    private ProxyInfo proxyInfo = ProxyInfo.NO_PROXY_INFO;
    private IntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.INFO);
    private Gson gson = new Gson();
    private AuthenticationSupport authenticationSupport = new AuthenticationSupport();
    private SSLContext sslContext = null;

    public JiraCloudRestConfigBuilder() {
        Set<BuilderPropertyKey> propertyKeys = new HashSet<>();
        propertyKeys.add(URL_KEY);
        propertyKeys.add(AUTH_USER_EMAIL);
        propertyKeys.add(ACCESS_TOKEN_KEY);
        propertyKeys.add(TIMEOUT_KEY);
        propertyKeys.add(PROXY_HOST_KEY);
        propertyKeys.add(PROXY_PORT_KEY);
        propertyKeys.add(PROXY_USERNAME_KEY);
        propertyKeys.add(PROXY_PASSWORD_KEY);
        propertyKeys.add(PROXY_NTLM_DOMAIN_KEY);
        propertyKeys.add(PROXY_NTLM_WORKSTATION_KEY);
        propertyKeys.add(TRUST_CERT_KEY);
        builderProperties = new BuilderProperties(propertyKeys);
        setProperty(TIMEOUT_KEY, Integer.toString(JiraCloudRestConfigBuilder.DEFAULT_TIMEOUT_SECONDS));
    }

    @Override
    protected JiraCloudRestConfig buildWithoutValidation() {
        URL jiraUrl = null;
        try {
            jiraUrl = new URL(getUrl());
        } catch (MalformedURLException e) {
            // ignored exception
        }

        if (getSslContext().isPresent()) {
            return new JiraCloudRestConfig(jiraUrl, getTimeoutInSeconds(), getProxyInfo(), getSslContext().get(), gson, authenticationSupport, getAuthUserEmail(), getApiToken());
        } else {
            return new JiraCloudRestConfig(jiraUrl, getTimeoutInSeconds(), getProxyInfo(), isTrustCert(), gson, authenticationSupport, getAuthUserEmail(), getApiToken());
        }
    }

    @Override
    protected void validate(BuilderStatus builderStatus) {
        if (StringUtils.isBlank(getUrl())) {
            builderStatus.addErrorMessage("The Jira Cloud url must be specified.");
        } else {
            try {
                URL blackDuckURL = new URL(getUrl());
                blackDuckURL.toURI();
            } catch (MalformedURLException | URISyntaxException e) {
                builderStatus.addErrorMessage(String.format("The provided Jira Cloud url (%s) is not a valid URL.", getUrl()));
            }
        }

        if (StringUtils.isBlank(getAuthUserEmail())) {
            builderStatus.addErrorMessage("The Jira Cloud user email must be specified.");
        }

        if (StringUtils.isBlank(getApiToken())) {
            builderStatus.addErrorMessage("The Jira Cloud access token must be specified.");
        }

        if (getTimeoutInSeconds() <= 0) {
            builderStatus.addErrorMessage("A timeout (in seconds) greater than zero must be specified.");
        }

        CredentialsBuilder proxyCredentialsBuilder = new CredentialsBuilder();
        proxyCredentialsBuilder.setUsername(getProxyUsername());
        proxyCredentialsBuilder.setPassword(getProxyPassword());
        BuilderStatus proxyCredentialsBuilderStatus = proxyCredentialsBuilder.validateAndGetBuilderStatus();
        if (!proxyCredentialsBuilderStatus.isValid()) {
            builderStatus.addErrorMessage("The proxy credentials were not valid.");
            builderStatus.addAllErrorMessages(proxyCredentialsBuilderStatus.getErrorMessages());
        } else {
            Credentials proxyCredentials = proxyCredentialsBuilder.build();
            ProxyInfoBuilder proxyInfoBuilder = new ProxyInfoBuilder();
            proxyInfoBuilder.setCredentials(proxyCredentials);
            proxyInfoBuilder.setHost(getProxyHost());
            proxyInfoBuilder.setPort(getProxyPort());
            proxyInfoBuilder.setNtlmDomain(getProxyNtlmDomain());
            proxyInfoBuilder.setNtlmWorkstation(getProxyNtlmWorkstation());
            BuilderStatus proxyInfoBuilderStatus = proxyInfoBuilder.validateAndGetBuilderStatus();
            if (!proxyInfoBuilderStatus.isValid()) {
                builderStatus.addAllErrorMessages(proxyInfoBuilderStatus.getErrorMessages());
            }
        }
    }

    public Set<String> getEnvironmentVariableKeys() {
        Set<String> allEnvironmentVariableKeys = new HashSet<>();
        allEnvironmentVariableKeys.addAll(builderProperties.getEnvironmentVariableKeys());
        return allEnvironmentVariableKeys;
    }

    public Map<BuilderPropertyKey, String> getProperties() {
        return builderProperties.getProperties();
    }

    public void setProperties(Set<? extends Map.Entry<String, String>> propertyEntries) {
        propertyEntries.forEach(entry -> setProperty(entry.getKey(), entry.getValue()));
    }

    public void setProperty(String key, String value) {
        setProperty(resolveKey(key), value);
    }

    private JiraCloudRestConfigBuilder setProperty(BuilderPropertyKey key, String value) {
        builderProperties.set(key, value);
        return this;
    }

    private BuilderPropertyKey resolveKey(String key) {
        String fixedKey = key.toUpperCase().replace(".", "_");
        return new BuilderPropertyKey(fixedKey);
    }

    private ProxyInfo getProxyInfo() {
        if (null != proxyInfo && !ProxyInfo.NO_PROXY_INFO.equals(proxyInfo)) {
            return proxyInfo;
        }
        if (StringUtils.isBlank(getProxyHost())) {
            return ProxyInfo.NO_PROXY_INFO;
        }

        CredentialsBuilder credentialsBuilder = Credentials.newBuilder();
        credentialsBuilder.setUsernameAndPassword(getProxyUsername(), getProxyPassword());
        Credentials proxyCredentials = credentialsBuilder.build();

        ProxyInfoBuilder proxyInfoBuilder = ProxyInfo.newBuilder();
        proxyInfoBuilder.setHost(getProxyHost());
        proxyInfoBuilder.setPort(getProxyPort());
        proxyInfoBuilder.setCredentials(proxyCredentials);
        proxyInfoBuilder.setNtlmDomain(getProxyNtlmDomain());
        proxyInfoBuilder.setNtlmWorkstation(getProxyNtlmWorkstation());

        return proxyInfoBuilder.build();
    }

    public void setProxyInfo(ProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
    }

    public IntLogger getLogger() {
        return logger;
    }

    public JiraCloudRestConfigBuilder setLogger(IntLogger logger) {
        if (null != logger) {
            this.logger = logger;
        }
        return this;
    }

    public Gson getGson() {
        return gson;
    }

    public JiraCloudRestConfigBuilder setGson(Gson gson) {
        if (null != gson) {
            this.gson = gson;
        }
        return this;
    }

    public AuthenticationSupport getAuthenticationSupport() {
        return authenticationSupport;
    }

    public JiraCloudRestConfigBuilder setAuthenticationSupport(AuthenticationSupport authenticationSupport) {
        if (null != authenticationSupport) {
            this.authenticationSupport = authenticationSupport;
        }
        return this;
    }

    public String getUrl() {
        return builderProperties.get(URL_KEY);
    }

    public JiraCloudRestConfigBuilder setUrl(String url) {
        return setProperty(URL_KEY, url);
    }

    public String getAuthUserEmail() {
        return builderProperties.get(AUTH_USER_EMAIL);
    }

    public JiraCloudRestConfigBuilder setAuthUserEmail(String authUserEmail) {
        return setProperty(AUTH_USER_EMAIL, authUserEmail);
    }

    public String getApiToken() {
        return builderProperties.get(ACCESS_TOKEN_KEY);
    }

    public JiraCloudRestConfigBuilder setApiToken(String accessToken) {
        return setProperty(ACCESS_TOKEN_KEY, accessToken);
    }

    public int getTimeoutInSeconds() {
        return NumberUtils.toInt(builderProperties.get(TIMEOUT_KEY), JiraCloudRestConfigBuilder.DEFAULT_TIMEOUT_SECONDS);
    }

    public JiraCloudRestConfigBuilder setTimeoutInSeconds(String timeout) {
        return setProperty(TIMEOUT_KEY, timeout);
    }

    public JiraCloudRestConfigBuilder setTimeoutInSeconds(int timeout) {
        return setTimeoutInSeconds(String.valueOf(timeout));
    }

    public String getProxyHost() {
        return builderProperties.get(PROXY_HOST_KEY);
    }

    public JiraCloudRestConfigBuilder setProxyHost(String proxyHost) {
        return setProperty(PROXY_HOST_KEY, proxyHost);
    }

    public int getProxyPort() {
        return NumberUtils.toInt(builderProperties.get(PROXY_PORT_KEY), 0);
    }

    public JiraCloudRestConfigBuilder setProxyPort(String proxyPort) {
        return setProperty(PROXY_PORT_KEY, proxyPort);
    }

    public JiraCloudRestConfigBuilder setProxyPort(int proxyPort) {
        return setProxyPort(String.valueOf(proxyPort));
    }

    public String getProxyUsername() {
        return builderProperties.get(PROXY_USERNAME_KEY);
    }

    public JiraCloudRestConfigBuilder setProxyUsername(String proxyUsername) {
        return setProperty(PROXY_USERNAME_KEY, proxyUsername);
    }

    public String getProxyPassword() {
        return builderProperties.get(PROXY_PASSWORD_KEY);
    }

    public JiraCloudRestConfigBuilder setProxyPassword(String proxyPassword) {
        return setProperty(PROXY_PASSWORD_KEY, proxyPassword);
    }

    public String getProxyNtlmDomain() {
        return builderProperties.get(PROXY_NTLM_DOMAIN_KEY);
    }

    public JiraCloudRestConfigBuilder setProxyNtlmDomain(String proxyNtlmDomain) {
        return setProperty(PROXY_NTLM_DOMAIN_KEY, proxyNtlmDomain);
    }

    public String getProxyNtlmWorkstation() {
        return builderProperties.get(PROXY_NTLM_WORKSTATION_KEY);
    }

    public JiraCloudRestConfigBuilder setProxyNtlmWorkstation(String proxyNtlmWorkstation) {
        return setProperty(PROXY_NTLM_WORKSTATION_KEY, proxyNtlmWorkstation);
    }

    public boolean isTrustCert() {
        return Boolean.parseBoolean(builderProperties.get(TRUST_CERT_KEY));
    }

    public JiraCloudRestConfigBuilder setTrustCert(String trustCert) {
        return setProperty(TRUST_CERT_KEY, trustCert);
    }

    public JiraCloudRestConfigBuilder setTrustCert(boolean trustCert) {
        return setTrustCert(String.valueOf(trustCert));
    }

    public Optional<SSLContext> getSslContext() {
        return Optional.ofNullable(sslContext);
    }

    public JiraCloudRestConfigBuilder setSslContext(SSLContext sslContext) {
        if (null != sslContext) {
            this.sslContext = sslContext;
        }
        return this;
    }
}
