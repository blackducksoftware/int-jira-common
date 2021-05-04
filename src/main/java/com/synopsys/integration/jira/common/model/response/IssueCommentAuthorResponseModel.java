/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class IssueCommentAuthorResponseModel extends JiraResponseModel {
    private String self;
    private String name;
    private String displayName;

    public IssueCommentAuthorResponseModel() {
    }

    public IssueCommentAuthorResponseModel(String self, String name, String displayName) {
        this.self = self;
        this.name = name;
        this.displayName = displayName;
    }

    public String getSelf() {
        return self;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
