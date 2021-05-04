/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

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
