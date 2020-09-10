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

import java.util.List;
import java.util.Map;

import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.IdComponent;
import com.synopsys.integration.jira.common.model.components.IssueFieldsComponent;
import com.synopsys.integration.jira.common.model.components.IssueIncludedFieldsComponent;
import com.synopsys.integration.jira.common.model.components.IssueUpdateMetadataComponent;
import com.synopsys.integration.jira.common.model.components.OperationsComponent;

public class IssueResponseModel extends JiraResponseModel {
    private String expand;
    private String id;
    private String self;
    private String key;
    private Map<String, Object> renderedFields;
    private Map<String, Object> properties;
    private Map<String, Object> names;
    private Map<String, Object> schema; // TODO maybe a Map<String, SchemaComponent> ?
    private List<IdComponent> transitions;
    private OperationsComponent operations;
    private IssueUpdateMetadataComponent editmeta;
    private PageOfChangelogsResponseModel changelog;
    private Object versionedRepresentations; // TODO
    private IssueIncludedFieldsComponent fieldsToInclude;
    private IssueFieldsComponent fields;

    public IssueResponseModel() {
    }

    public String getExpand() {
        return expand;
    }

    public String getId() {
        return id;
    }

    public String getSelf() {
        return self;
    }

    public String getKey() {
        return key;
    }

    public Map<String, Object> getRenderedFields() {
        return renderedFields;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Map<String, Object> getNames() {
        return names;
    }

    public Map<String, Object> getSchema() {
        return schema;
    }

    public List<IdComponent> getTransitions() {
        return transitions;
    }

    public OperationsComponent getOperations() {
        return operations;
    }

    public IssueUpdateMetadataComponent getEditmeta() {
        return editmeta;
    }

    public PageOfChangelogsResponseModel getChangelog() {
        return changelog;
    }

    public Object getVersionedRepresentations() {
        return versionedRepresentations;
    }

    public IssueIncludedFieldsComponent getFieldsToInclude() {
        return fieldsToInclude;
    }

    public IssueFieldsComponent getFields() {
        return fields;
    }
}
