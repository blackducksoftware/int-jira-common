/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
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
    private Map<String, JsonElement> renderedFields;
    private Map<String, JsonElement> properties;
    private Map<String, JsonElement> names;
    private Map<String, JsonElement> schema; // TODO maybe a Map<String, SchemaComponent> ?
    private List<IdComponent> transitions;
    private OperationsComponent operations;
    private IssueUpdateMetadataComponent editmeta;
    private PageOfChangelogsResponseModel changelog;
    private JsonElement versionedRepresentations; // TODO
    private IssueIncludedFieldsComponent fieldsToInclude;
    private IssueFieldsComponent fields;

    public IssueResponseModel() {
    }

    public IssueResponseModel(
        String expand,
        String id,
        String self,
        String key,
        Map<String, JsonElement> renderedFields,
        Map<String, JsonElement> properties,
        Map<String, JsonElement> names,
        Map<String, JsonElement> schema,
        List<IdComponent> transitions,
        OperationsComponent operations,
        IssueUpdateMetadataComponent editmeta,
        PageOfChangelogsResponseModel changelog,
        JsonElement versionedRepresentations,
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

    public Map<String, JsonElement> getRenderedFields() {
        return renderedFields;
    }

    public Map<String, JsonElement> getProperties() {
        return properties;
    }

    public Map<String, JsonElement> getNames() {
        return names;
    }

    public Map<String, JsonElement> getSchema() {
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

    public JsonElement getVersionedRepresentations() {
        return versionedRepresentations;
    }

    public IssueIncludedFieldsComponent getFieldsToInclude() {
        return fieldsToInclude;
    }

    public IssueFieldsComponent getFields() {
        return fields;
    }
}
