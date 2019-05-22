package com.synopsys.integration.jira.common.cloud.model.request;

import java.util.Map;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class WorkflowSchemeRequestModel extends JiraComponent {
    private String name;
    private String description;
    private String defaultWorkflow;
    private Map<String, String> issueTypeMappings;
    private Boolean updateDraftIfNeeded;

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
