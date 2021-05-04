/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.server.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class IssueSearchResponseModel extends JiraPageResponseModel {
    private String expand;
    private List<IssueSearchIssueComponent> issues;

    public IssueSearchResponseModel() {
    }

    public IssueSearchResponseModel(String expand, List<IssueSearchIssueComponent> issues) {
        this.expand = expand;
        this.issues = issues;
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

    public List<IssueSearchIssueComponent> getIssues() {
        return issues;
    }

}
