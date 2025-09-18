/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.model;

import java.util.List;
import java.util.Map;

import com.blackduck.integration.jira.common.model.JiraResponseModel;
import com.blackduck.integration.jira.common.model.components.IdComponent;
import com.blackduck.integration.jira.common.model.response.IssueResponseModel;
import com.blackduck.integration.jira.common.server.model.component.JiraServerIssueFieldsComponent;
import com.blackduck.integration.jira.common.model.components.IssueIncludedFieldsComponent;
import com.blackduck.integration.jira.common.model.components.IssueUpdateMetadataComponent;
import com.blackduck.integration.jira.common.model.components.OperationsComponent;
import com.blackduck.integration.jira.common.model.response.PageOfChangelogsResponseModel;
import com.google.gson.JsonElement;

public class JiraServerIssueResponseModel extends JiraResponseModel implements IssueResponseModel {
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
    private JsonElement versionedRepresentations;
    private IssueIncludedFieldsComponent fieldsToInclude;
    private JiraServerIssueFieldsComponent fields;

    public JiraServerIssueResponseModel() {
    }

    public JiraServerIssueResponseModel(
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
        JiraServerIssueFieldsComponent fields
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

    public JiraServerIssueFieldsComponent getFields() {
        return fields;
    }
}
