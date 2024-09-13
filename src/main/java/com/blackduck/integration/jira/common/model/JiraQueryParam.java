/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model;

import com.blackduck.integration.jira.common.enumeration.JiraSortOrder;

public class JiraQueryParam {
    private String key;
    private String value;
    private JiraSortOrder jiraSortOrder;

    public JiraQueryParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JiraQueryParam(String key, String value, JiraSortOrder jiraSortOrder) {
        this.key = key;
        this.value = value;
        this.jiraSortOrder = jiraSortOrder;
    }

    public String createQueryString() {
        if (null != jiraSortOrder) {
            return key + "=" + jiraSortOrder.getQueryValuePrefix() + value;
        }
        return key + "=" + value;
    }

}
