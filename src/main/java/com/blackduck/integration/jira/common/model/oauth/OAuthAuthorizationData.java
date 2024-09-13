/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.oauth;

public class OAuthAuthorizationData {
    private String authorizationUrl;
    private String authorizationToken;

    public OAuthAuthorizationData(String authorizationUrl, String authorizationToken) {
        this.authorizationUrl = authorizationUrl;
        this.authorizationToken = authorizationToken;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

}

