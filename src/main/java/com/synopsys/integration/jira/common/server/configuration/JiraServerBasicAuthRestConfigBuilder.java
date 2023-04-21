package com.synopsys.integration.jira.common.server.configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.builder.BuilderPropertyKey;
import com.synopsys.integration.builder.BuilderStatus;

public class JiraServerBasicAuthRestConfigBuilder extends JiraServerRestConfigBuilder<JiraServerBasicAuthRestConfigBuilder, JiraServerBasicAuthRestConfig> {
    public static final BuilderPropertyKey AUTH_USERNAME = new BuilderPropertyKey("JIRA_AUTH_USERNAME");
    public static final BuilderPropertyKey AUTH_PASSWORD = new BuilderPropertyKey("JIRA_AUTH_PASSWORD");

    @Override
    protected JiraServerBasicAuthRestConfigBuilder getThis() {
        return this;
    }

    @Override
    public Set<BuilderPropertyKey> getAuthenticationProperties() {
        Set<BuilderPropertyKey> propertyKeys = new HashSet<>();
        propertyKeys.add(AUTH_USERNAME);
        propertyKeys.add(AUTH_PASSWORD);
        return propertyKeys;
    }

    @Override
    protected JiraServerBasicAuthRestConfig buildWithoutValidation() {
        URL jiraUrl = null;
        try {
            jiraUrl = new URL(getUrl());
        } catch (MalformedURLException e) {
        }

        return new JiraServerBasicAuthRestConfig(
            jiraUrl,
            getTimeoutInSeconds(),
            getProxyInfo(),
            isTrustCert(),
            getGson(),
            getAuthenticationSupport(),
            getAuthUsername(),
            getAuthPassword()
        );
    }

    @Override
    public void validateAuthenticationProperties(BuilderStatus builderStatus) {
        if (StringUtils.isBlank(getAuthUsername())) {
            builderStatus.addErrorMessage("The Jira server user name must be specified.");
        }

        if (StringUtils.isBlank(getAuthPassword())) {
            builderStatus.addErrorMessage("The Jira server password must be specified.");
        }
    }

    public String getAuthUsername() {
        return getProperties().get(AUTH_USERNAME);
    }

    public JiraServerBasicAuthRestConfigBuilder setAuthUsername(String authUsername) {
        setProperty(AUTH_USERNAME, authUsername);
        return this;
    }

    public String getAuthPassword() {
        return getProperties().get(AUTH_PASSWORD);
    }

    public JiraServerBasicAuthRestConfigBuilder setAuthPassword(String authPassword) {
        setProperty(AUTH_PASSWORD, authPassword);
        return this;
    }

}
