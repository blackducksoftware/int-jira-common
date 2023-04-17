/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class IssueCreateMetadataResponseModel extends JiraResponseModel {
    private String expand;
    private List<ProjectIssueCreateMetadataResponseModel> projects;

    public IssueCreateMetadataResponseModel() {
    }

    public IssueCreateMetadataResponseModel(String expand, List<ProjectIssueCreateMetadataResponseModel> projects) {
        this.expand = expand;
        this.projects = projects;
    }

    public String getExpand() {
        return expand;
    }

    public List<ProjectIssueCreateMetadataResponseModel> getProjects() {
        return projects;
    }
}
