/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.enumeration;

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
