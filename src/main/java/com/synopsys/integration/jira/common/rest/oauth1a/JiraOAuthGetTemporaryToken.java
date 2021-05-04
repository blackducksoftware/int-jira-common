/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.oauth1a;

import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;

public class JiraOAuthGetTemporaryToken extends OAuthGetTemporaryToken {
    public JiraOAuthGetTemporaryToken(String temporaryTokenRequestUrl) {
        super(temporaryTokenRequestUrl);
        this.usePost = true;
    }

}
