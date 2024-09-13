/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.JiraResponseModel;

public class DefaultWorkflowResponseModel extends JiraResponseModel {
    private String name;
    private Boolean updateDraftIfNeeded;

    public DefaultWorkflowResponseModel() {
    }

    public DefaultWorkflowResponseModel(String name, Boolean updateDraftIfNeeded) {
        this.name = name;
        this.updateDraftIfNeeded = updateDraftIfNeeded;
    }

    public String getName() {
        return name;
    }

    public Boolean getUpdateDraftIfNeeded() {
        return updateDraftIfNeeded;
    }
}
