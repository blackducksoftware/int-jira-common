/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.JiraResponseModel;

public class IssueCreationResponseModel extends JiraResponseModel {
    private String id;
    private String self;
    private String key;

    public IssueCreationResponseModel() {
    }

    public IssueCreationResponseModel(String id, String self, String key) {
        this.id = id;
        this.self = self;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getSelf() {
        return self;
    }

    public String getKey() {
        return key;
    }
}
