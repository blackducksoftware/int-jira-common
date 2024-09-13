/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.oauth1a;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

public class JiraOAuthServiceFactory {
    public JiraOAuthService fromJiraServerUrl(String jiraServerUrl) throws NoSuchAlgorithmException {
        KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
        return new JiraOAuthService(jiraServerUrl, rsaKeyFactory);
    }

}

