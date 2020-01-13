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
