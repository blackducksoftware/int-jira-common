/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.oauth1a;

import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;

public class JiraOAuthGetTemporaryToken extends OAuthGetTemporaryToken {
    public JiraOAuthGetTemporaryToken(String temporaryTokenRequestUrl) {
        super(temporaryTokenRequestUrl);
        this.usePost = true;
    }

}
