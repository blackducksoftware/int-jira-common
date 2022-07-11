/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class IssueTypesWorkflowMappingResponseModel extends JiraResponseModel {
    private String workflow;
    private List<String> issueTypes;
    private Boolean defaultMapping;
    private Boolean updateDraftIfNeeded;

    public IssueTypesWorkflowMappingResponseModel() {
    }

    public IssueTypesWorkflowMappingResponseModel(String workflow, List<String> issueTypes, Boolean defaultMapping, Boolean updateDraftIfNeeded) {
        this.workflow = workflow;
        this.issueTypes = issueTypes;
        this.defaultMapping = defaultMapping;
        this.updateDraftIfNeeded = updateDraftIfNeeded;
    }

    public String getWorkflow() {
        return workflow;
    }

    public List<String> getIssueTypes() {
        return issueTypes;
    }

    public Boolean getDefaultMapping() {
        return defaultMapping;
    }

    public Boolean getUpdateDraftIfNeeded() {
        return updateDraftIfNeeded;
    }
}
