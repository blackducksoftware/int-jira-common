/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.enumeration;

public enum JiraSortOrder {
    ASCENDING("+"),
    DESCENDING("-");

    private String queryValuePrefix;

    private JiraSortOrder(String queryValuePrefix) {
        this.queryValuePrefix = queryValuePrefix;
    }

    public String getQueryValuePrefix() {
        return queryValuePrefix;
    }

}
