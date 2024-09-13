/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import java.util.List;

import com.blackduck.integration.jira.common.model.JiraResponseModel;
import com.blackduck.integration.jira.common.model.components.SchemaComponent;
import com.google.gson.JsonElement;

public class IssueCreatemetaFieldResponseModel extends JiraResponseModel {
    private boolean required;
    private SchemaComponent schema;
    private String name;
    private String key;
    private boolean hasDefaultValue;
    private List<String> operations;
    private List<JsonElement> allowedValues;

    public IssueCreatemetaFieldResponseModel() {
    }

    public IssueCreatemetaFieldResponseModel(
        boolean required,
        SchemaComponent schema,
        String name,
        String key,
        boolean hasDefaultValue,
        List<String> operations,
        List<JsonElement> allowedValues
    ) {
        this.required = required;
        this.schema = schema;
        this.name = name;
        this.key = key;
        this.hasDefaultValue = hasDefaultValue;
        this.operations = operations;
        this.allowedValues = allowedValues;
    }

    public boolean isRequired() {
        return required;
    }

    public SchemaComponent getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public boolean isHasDefaultValue() {
        return hasDefaultValue;
    }

    public List<String> getOperations() {
        return operations;
    }

    public List<JsonElement> getAllowedValues() {
        return allowedValues;
    }
}
