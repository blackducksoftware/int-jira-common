/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.server.configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.builder.BuilderPropertyKey;
import com.synopsys.integration.builder.BuilderStatus;

public class JiraServerBearerAuthRestConfigBuilder extends JiraServerRestConfigBuilder<JiraServerBearerAuthRestConfigBuilder, JiraServerBearerAuthRestConfig> {
    public static final BuilderPropertyKey AUTH_PERSONAL_ACCESS_TOKEN = new BuilderPropertyKey("JIRA_AUTH_PERSONAL_ACCESS_TOKEN");

    @Override
    protected JiraServerBearerAuthRestConfigBuilder getThis() {
        return this;
    }

    @Override
    public Set<BuilderPropertyKey> getAuthenticationProperties() {
        Set<BuilderPropertyKey> propertyKeys = new HashSet<>();
        propertyKeys.add(AUTH_PERSONAL_ACCESS_TOKEN);
        return propertyKeys;
    }

    @Override
    public void validateAuthenticationProperties(BuilderStatus builderStatus) {
        if (StringUtils.isBlank(getAccessToken())) {
            builderStatus.addErrorMessage("The Jira server user name must be specified.");
        }
    }

    @Override
    protected JiraServerBearerAuthRestConfig buildWithoutValidation() {
        URL jiraUrl = null;
        try {
            jiraUrl = new URL(getUrl());
        } catch (MalformedURLException e) {
        }

        return new JiraServerBearerAuthRestConfig(
            jiraUrl,
            getTimeoutInSeconds(),
            getProxyInfo(),
            isTrustCert(),
            getGson(),
            getAuthenticationSupport(),
            getAccessToken()
        );
    }

    public String getAccessToken() {
        return getProperties().get(AUTH_PERSONAL_ACCESS_TOKEN);
    }

    public JiraServerBearerAuthRestConfigBuilder setAccessToken(String accessToken) {
        setProperty(AUTH_PERSONAL_ACCESS_TOKEN, accessToken);
        return this;
    }
}
