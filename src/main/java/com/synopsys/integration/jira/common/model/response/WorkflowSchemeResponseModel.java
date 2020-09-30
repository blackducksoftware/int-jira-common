/**
 * int-jira-common
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.Map;

import com.google.gson.JsonObject;
import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.UserDetailsComponent;

public class WorkflowSchemeResponseModel extends JiraResponseModel {
    private Integer id;
    private String self;
    private String name;
    private String description;
    private String defaultWorkflow;
    private Map<String, JsonObject> issueTypeMappings; // TODO implement
    private String originalDefaultWorkflow;
    private Map<String, JsonObject> originalIssueTypeMappings;
    private Boolean draft;
    private UserDetailsComponent lastModifiedUser;
    private String lastModified;
    private Boolean updateDraftIfNeeded;
    private Map<String, JsonObject> issueTypes; // TODO implement

    public WorkflowSchemeResponseModel() {
    }

    public WorkflowSchemeResponseModel(
        Integer id,
        String self,
        String name,
        String description,
        String defaultWorkflow,
        Map<String, JsonObject> issueTypeMappings,
        String originalDefaultWorkflow,
        Map<String, JsonObject> originalIssueTypeMappings,
        Boolean draft,
        UserDetailsComponent lastModifiedUser,
        String lastModified,
        Boolean updateDraftIfNeeded,
        Map<String, JsonObject> issueTypes
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

    public Map<String, JsonObject> getIssueTypeMappings() {
        return issueTypeMappings;
    }

    public String getOriginalDefaultWorkflow() {
        return originalDefaultWorkflow;
    }

    public Map<String, JsonObject> getOriginalIssueTypeMappings() {
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

    public Map<String, JsonObject> getIssueTypes() {
        return issueTypes;
    }
}
