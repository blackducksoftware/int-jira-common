/**
 * int-jira-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.components.SchemaComponent;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;

public class IssueSearchResponseModel extends JiraPageResponseModel {
    private String expand;
    private List<IssueResponseModel> issues;
    private List<String> warningMessages;
    private Object names; // TODO create a bean for this object
    private SchemaComponent schema;

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public List<IssueResponseModel> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueResponseModel> issues) {
        this.issues = issues;
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public Object getNames() {
        return names;
    }

    public void setNames(Object names) {
        this.names = names;
    }

    public SchemaComponent getSchema() {
        return schema;
    }

    public void setSchema(SchemaComponent schema) {
        this.schema = schema;
    }

}
