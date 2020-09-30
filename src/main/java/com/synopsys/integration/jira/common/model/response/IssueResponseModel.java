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

import com.google.gson.JsonObject;
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
    private Map<String, JsonObject> renderedFields;
    private Map<String, JsonObject> properties;
    private Map<String, JsonObject> names;
    private Map<String, JsonObject> schema; // TODO maybe a Map<String, SchemaComponent> ?
    private List<IdComponent> transitions;
    private OperationsComponent operations;
    private IssueUpdateMetadataComponent editmeta;
    private PageOfChangelogsResponseModel changelog;
    private JsonObject versionedRepresentations; // TODO
    private IssueIncludedFieldsComponent fieldsToInclude;
    private IssueFieldsComponent fields;

    public IssueResponseModel() {
    }

    public IssueResponseModel(
        String expand,
        String id,
        String self,
        String key,
        Map<String, JsonObject> renderedFields,
        Map<String, JsonObject> properties,
        Map<String, JsonObject> names,
        Map<String, JsonObject> schema,
        List<IdComponent> transitions,
        OperationsComponent operations,
        IssueUpdateMetadataComponent editmeta,
        PageOfChangelogsResponseModel changelog,
        JsonObject versionedRepresentations,
        IssueIncludedFieldsComponent fieldsToInclude,
        IssueFieldsComponent fields
    ) {
        this.expand = expand;
        this.id = id;
        this.self = self;
        this.key = key;
        this.renderedFields = renderedFields;
        this.properties = properties;
        this.names = names;
        this.schema = schema;
        this.transitions = transitions;
        this.operations = operations;
        this.editmeta = editmeta;
        this.changelog = changelog;
        this.versionedRepresentations = versionedRepresentations;
        this.fieldsToInclude = fieldsToInclude;
        this.fields = fields;
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

    public Map<String, JsonObject> getRenderedFields() {
        return renderedFields;
    }

    public Map<String, JsonObject> getProperties() {
        return properties;
    }

    public Map<String, JsonObject> getNames() {
        return names;
    }

    public Map<String, JsonObject> getSchema() {
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

    public JsonObject getVersionedRepresentations() {
        return versionedRepresentations;
    }

    public IssueIncludedFieldsComponent getFieldsToInclude() {
        return fieldsToInclude;
    }

    public IssueFieldsComponent getFields() {
        return fields;
    }
}
