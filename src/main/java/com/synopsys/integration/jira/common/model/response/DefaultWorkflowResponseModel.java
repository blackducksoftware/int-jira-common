/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

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
