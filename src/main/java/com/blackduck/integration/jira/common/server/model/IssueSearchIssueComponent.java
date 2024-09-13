/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.blackduck.integration.rest.component.IntRestComponent;

public class IssueSearchIssueComponent extends IntRestComponent {
    private String expand;
    private String id;
    private String key;
    private IssueSearchIssueFieldsComponent fields;

    public IssueSearchIssueComponent() {
    }

    public IssueSearchIssueComponent(String expand, String id, String key, IssueSearchIssueFieldsComponent fields) {
        this.expand = expand;
        this.id = id;
        this.key = key;
        this.fields = fields;
    }

    public String getExpand() {
        return expand;
    }

    public List<String> getExpandAsList() {
        if (StringUtils.isNotBlank(expand)) {
            String[] expandedArray = expand.split(",");
            return Arrays.asList(expandedArray);
        }
        return Collections.emptyList();
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public IssueSearchIssueFieldsComponent getFields() {
        return fields;
    }

}
