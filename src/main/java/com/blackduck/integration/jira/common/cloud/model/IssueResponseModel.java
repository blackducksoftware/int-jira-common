/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.model;

import com.blackduck.integration.jira.common.cloud.model.component.IssueFieldsComponent;
import com.blackduck.integration.jira.common.model.JiraResponseModel;
import com.blackduck.integration.jira.common.model.components.IdComponent;
import com.blackduck.integration.jira.common.model.components.IssueIncludedFieldsComponent;
import com.blackduck.integration.jira.common.model.components.IssueUpdateMetadataComponent;
import com.blackduck.integration.jira.common.model.components.OperationsComponent;
import com.blackduck.integration.jira.common.model.response.PageOfChangelogsResponseModel;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

public class IssueResponseModel extends JiraResponseModel {
    private String expand;
    private String id;
    private String self;
    private String key;
    private Map<String, JsonElement> renderedFields;
    private Map<String, JsonElement> properties;
    private Map<String, JsonElement> names;
    private OperationsComponent operations;
    private IssueUpdateMetadataComponent editmeta;
    private PageOfChangelogsResponseModel changelog;
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
        OperationsComponent operations,
        IssueUpdateMetadataComponent editmeta,
        PageOfChangelogsResponseModel changelog,
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
        this.operations = operations;
        this.editmeta = editmeta;
        this.changelog = changelog;
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

    public OperationsComponent getOperations() {
        return operations;
    }

    public IssueUpdateMetadataComponent getEditmeta() {
        return editmeta;
    }

    public PageOfChangelogsResponseModel getChangelog() {
        return changelog;
    }

    public IssueIncludedFieldsComponent getFieldsToInclude() {
        return fieldsToInclude;
    }

    public IssueFieldsComponent getFields() {
        return fields;
    }
}
