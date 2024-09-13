/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.configuration;

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

public abstract class JiraServerRestConfigBuilder<S extends JiraServerRestConfigBuilder<S, T>, T extends JiraServerRestConfig> extends IntegrationBuilder<T> {
    public static final BuilderPropertyKey URL_KEY = new BuilderPropertyKey("JIRA_URL");
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

    protected JiraServerRestConfigBuilder() {
        Set<BuilderPropertyKey> propertyKeys = new HashSet<>();
        propertyKeys.add(URL_KEY);
        propertyKeys.add(TIMEOUT_KEY);
        propertyKeys.add(PROXY_HOST_KEY);
        propertyKeys.add(PROXY_PORT_KEY);
        propertyKeys.add(PROXY_USERNAME_KEY);
        propertyKeys.add(PROXY_PASSWORD_KEY);
        propertyKeys.add(PROXY_NTLM_DOMAIN_KEY);
        propertyKeys.add(PROXY_NTLM_WORKSTATION_KEY);
        propertyKeys.add(TRUST_CERT_KEY);
        propertyKeys.addAll(getAuthenticationProperties());
        builderProperties = new BuilderProperties(propertyKeys);
        setProperty(TIMEOUT_KEY, Integer.toString(JiraServerRestConfigBuilder.DEFAULT_TIMEOUT_SECONDS));
    }

    // Since this factory is abstract, we cannot return "this" when calling setters since "this" would be an implementation of JiraServerRestConfigBuilder.
    //  By making this factory generic we can instead use getThis() to return the implementation JiraServerRestConfigBuilder.
    protected abstract S getThis();

    public abstract Set<BuilderPropertyKey> getAuthenticationProperties();

    public abstract void validateAuthenticationProperties(BuilderStatus builderStatus);

    @Override
    protected void validate(BuilderStatus builderStatus) {
        if (StringUtils.isBlank(getUrl())) {
            builderStatus.addErrorMessage("The Jira server url must be specified.");
        } else {
            try {
                URL blackDuckURL = new URL(getUrl());
                blackDuckURL.toURI();
            } catch (MalformedURLException | URISyntaxException e) {
                builderStatus.addErrorMessage(String.format("The provided Jira server url (%s) is not a valid URL.", getUrl()));
            }
        }

        validateAuthenticationProperties(builderStatus);

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

    public S setProperty(String key, String value) {
        return setProperty(resolveKey(key), value);
    }

    public S setProperty(BuilderPropertyKey key, String value) {
        builderProperties.setProperty(key.getKey(), value);
        return getThis();
    }


    private BuilderPropertyKey resolveKey(String key) {
        String fixedKey = key.toUpperCase().replace(".", "_");
        return new BuilderPropertyKey(fixedKey);
    }

    protected ProxyInfo getProxyInfo() {
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

    public S setLogger(IntLogger logger) {
        if (null != logger) {
            this.logger = logger;
        }
        return getThis();
    }

    public Gson getGson() {
        return gson;
    }

    public S setGson(Gson gson) {
        if (null != gson) {
            this.gson = gson;
        }
        return getThis();
    }

    public AuthenticationSupport getAuthenticationSupport() {
        return authenticationSupport;
    }

    public S setAuthenticationSupport(AuthenticationSupport authenticationSupport) {
        if (null != authenticationSupport) {
            this.authenticationSupport = authenticationSupport;
        }
        return getThis();
    }

    public String getUrl() {
        return builderProperties.get(URL_KEY);
    }

    public S setUrl(String url) {
        return setProperty(URL_KEY, url);
    }

    public int getTimeoutInSeconds() {
        return NumberUtils.toInt(builderProperties.get(TIMEOUT_KEY), JiraServerRestConfigBuilder.DEFAULT_TIMEOUT_SECONDS);
    }

    public S setTimeoutInSeconds(String timeout) {
        return setProperty(TIMEOUT_KEY, timeout);
    }

    public S setTimeoutInSeconds(int timeout) {
        return setTimeoutInSeconds(String.valueOf(timeout));
    }

    public String getProxyHost() {
        return builderProperties.get(PROXY_HOST_KEY);
    }

    public S setProxyHost(String proxyHost) {
        return setProperty(PROXY_HOST_KEY, proxyHost);
    }

    public int getProxyPort() {
        return NumberUtils.toInt(builderProperties.get(PROXY_PORT_KEY), 0);
    }

    public S setProxyPort(String proxyPort) {
        return setProperty(PROXY_PORT_KEY, proxyPort);
    }

    public S setProxyPort(int proxyPort) {
        return setProxyPort(String.valueOf(proxyPort));
    }

    public String getProxyUsername() {
        return builderProperties.get(PROXY_USERNAME_KEY);
    }

    public S setProxyUsername(String proxyUsername) {
        return setProperty(PROXY_USERNAME_KEY, proxyUsername);
    }

    public String getProxyPassword() {
        return builderProperties.get(PROXY_PASSWORD_KEY);
    }

    public S setProxyPassword(String proxyPassword) {
        return setProperty(PROXY_PASSWORD_KEY, proxyPassword);
    }

    public String getProxyNtlmDomain() {
        return builderProperties.get(PROXY_NTLM_DOMAIN_KEY);
    }

    public S setProxyNtlmDomain(String proxyNtlmDomain) {
        return setProperty(PROXY_NTLM_DOMAIN_KEY, proxyNtlmDomain);
    }

    public String getProxyNtlmWorkstation() {
        return builderProperties.get(PROXY_NTLM_WORKSTATION_KEY);
    }

    public S setProxyNtlmWorkstation(String proxyNtlmWorkstation) {
        return setProperty(PROXY_NTLM_WORKSTATION_KEY, proxyNtlmWorkstation);
    }

    public boolean isTrustCert() {
        return Boolean.parseBoolean(builderProperties.get(TRUST_CERT_KEY));
    }

    public S setTrustCert(String trustCert) {
        return setProperty(TRUST_CERT_KEY, trustCert);
    }

    public S setTrustCert(boolean trustCert) {
        return setTrustCert(String.valueOf(trustCert));
    }

    public Optional<SSLContext> getSslContext() {
        return Optional.ofNullable(sslContext);
    }

    public S setSslContext(SSLContext sslContext) {
        if (sslContext != null) {
            this.sslContext = sslContext;
        }
        return getThis();
    }
}
