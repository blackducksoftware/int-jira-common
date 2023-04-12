/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class ProjectIssueCreateMetadataResponseModel extends JiraResponseModel {
    private String expand;
    private String self;
    private String id;
    private String key;
    private String name;
    @SerializedName("issuetypes")
    private List<IssueTypeResponseModel> issueTypes;

    public ProjectIssueCreateMetadataResponseModel() {
    }

    public ProjectIssueCreateMetadataResponseModel(String expand, String self, String id, String key, String name, List<IssueTypeResponseModel> issueTypes) {
        this.expand = expand;
        this.self = self;
        this.id = id;
        this.key = key;
        this.name = name;
        this.issueTypes = issueTypes;
    }

    public String getExpand() {
        return expand;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<IssueTypeResponseModel> getIssueTypes() {
        return issueTypes;
    }
}
