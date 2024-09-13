/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.JiraResponseModel;

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
