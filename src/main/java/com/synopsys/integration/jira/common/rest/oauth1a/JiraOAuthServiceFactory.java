/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.oauth1a;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

public class JiraOAuthServiceFactory {
    public JiraOAuthService fromJiraServerUrl(String jiraServerUrl) throws NoSuchAlgorithmException {
        KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
        return new JiraOAuthService(jiraServerUrl, rsaKeyFactory);
    }

}

