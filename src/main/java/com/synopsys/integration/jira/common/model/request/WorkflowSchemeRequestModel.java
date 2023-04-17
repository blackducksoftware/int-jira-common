/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.request;

import java.util.Map;

public class WorkflowSchemeRequestModel extends JiraRequestModel {
    private final String name;
    private final String description;
    private final String defaultWorkflow;
    private final Map<String, String> issueTypeMappings;
    private final Boolean updateDraftIfNeeded;

    public WorkflowSchemeRequestModel(final String name, final String description, final String defaultWorkflow, final Map<String, String> issueTypeMappings, final Boolean updateDraftIfNeeded) {
        this.name = name;
        this.description = description;
        this.defaultWorkflow = defaultWorkflow;
        this.issueTypeMappings = issueTypeMappings;
        this.updateDraftIfNeeded = updateDraftIfNeeded;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDefaultWorkflow() {
        return defaultWorkflow;
    }

    public Map<String, String> getIssueTypeMappings() {
        return issueTypeMappings;
    }

    public Boolean getUpdateDraftIfNeeded() {
        return updateDraftIfNeeded;
    }

}
