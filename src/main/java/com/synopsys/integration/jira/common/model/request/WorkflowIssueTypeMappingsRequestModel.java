/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.request;

import java.util.List;

public class WorkflowIssueTypeMappingsRequestModel extends JiraRequestModel {
    private String id;
    private String workflow;
    private List<String> issueTypes;
    private Boolean defaultMapping;
    private Boolean updateDraftIfNeeded;

    public WorkflowIssueTypeMappingsRequestModel() {
    }

    public WorkflowIssueTypeMappingsRequestModel(final String id, final String workflow, final List<String> issueTypes, final Boolean defaultMapping, final Boolean updateDraftIfNeeded) {
        this.id = id;
        this.workflow = workflow;
        this.issueTypes = issueTypes;
        this.defaultMapping = defaultMapping;
        this.updateDraftIfNeeded = updateDraftIfNeeded;
    }

    public String getId() {
        return id;
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
