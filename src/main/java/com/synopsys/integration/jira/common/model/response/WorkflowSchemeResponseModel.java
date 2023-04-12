/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.Map;

import com.google.gson.JsonElement;
import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.UserDetailsComponent;

public class WorkflowSchemeResponseModel extends JiraResponseModel {
    private Integer id;
    private String self;
    private String name;
    private String description;
    private String defaultWorkflow;
    private Map<String, JsonElement> issueTypeMappings; // TODO implement
    private String originalDefaultWorkflow;
    private Map<String, JsonElement> originalIssueTypeMappings;
    private Boolean draft;
    private UserDetailsComponent lastModifiedUser;
    private String lastModified;
    private Boolean updateDraftIfNeeded;
    private Map<String, JsonElement> issueTypes; // TODO implement

    public WorkflowSchemeResponseModel() {
    }

    public WorkflowSchemeResponseModel(
        Integer id,
        String self,
        String name,
        String description,
        String defaultWorkflow,
        Map<String, JsonElement> issueTypeMappings,
        String originalDefaultWorkflow,
        Map<String, JsonElement> originalIssueTypeMappings,
        Boolean draft,
        UserDetailsComponent lastModifiedUser,
        String lastModified,
        Boolean updateDraftIfNeeded,
        Map<String, JsonElement> issueTypes
    ) {
        this.id = id;
        this.self = self;
        this.name = name;
        this.description = description;
        this.defaultWorkflow = defaultWorkflow;
        this.issueTypeMappings = issueTypeMappings;
        this.originalDefaultWorkflow = originalDefaultWorkflow;
        this.originalIssueTypeMappings = originalIssueTypeMappings;
        this.draft = draft;
        this.lastModifiedUser = lastModifiedUser;
        this.lastModified = lastModified;
        this.updateDraftIfNeeded = updateDraftIfNeeded;
        this.issueTypes = issueTypes;
    }

    public Integer getId() {
        return id;
    }

    public String getSelf() {
        return self;
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

    public Map<String, JsonElement> getIssueTypeMappings() {
        return issueTypeMappings;
    }

    public String getOriginalDefaultWorkflow() {
        return originalDefaultWorkflow;
    }

    public Map<String, JsonElement> getOriginalIssueTypeMappings() {
        return originalIssueTypeMappings;
    }

    public Boolean getDraft() {
        return draft;
    }

    public UserDetailsComponent getLastModifiedUser() {
        return lastModifiedUser;
    }

    public String getLastModified() {
        return lastModified;
    }

    public Boolean getUpdateDraftIfNeeded() {
        return updateDraftIfNeeded;
    }

    public Map<String, JsonElement> getIssueTypes() {
        return issueTypes;
    }
}
