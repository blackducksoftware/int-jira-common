/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.request;

public class WorkflowRequestModel extends JiraRequestModel {
    private String schemeId;
    private String workflow;
    private Boolean updateDraftIfNeeded;

    public WorkflowRequestModel() {
    }

    public WorkflowRequestModel(final String schemeId, final String workflow, final Boolean updateDraftIfNeeded) {
        this.schemeId = schemeId;
        this.workflow = workflow;
        this.updateDraftIfNeeded = updateDraftIfNeeded;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public String getWorkflow() {
        return workflow;
    }

    public Boolean getUpdateDraftIfNeeded() {
        return updateDraftIfNeeded;
    }
}
