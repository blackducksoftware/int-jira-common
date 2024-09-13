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

public class CustomFieldCreationResponseModel extends JiraResponseModel {
    private String id;
    private String key;
    private String name;
    private Boolean custom;
    private Boolean navigable;
    private Boolean searchable;
    private List<String> clauseNames;
    private SchemaComponent schema;

    public CustomFieldCreationResponseModel() {
    }

    public CustomFieldCreationResponseModel(
        String id,
        String key,
        String name,
        Boolean custom,
        Boolean navigable,
        Boolean searchable,
        List<String> clauseNames,
        SchemaComponent schema
    ) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.custom = custom;
        this.navigable = navigable;
        this.searchable = searchable;
        this.clauseNames = clauseNames;
        this.schema = schema;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Boolean getCustom() {
        return custom;
    }

    public Boolean getNavigable() {
        return navigable;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public List<String> getClauseNames() {
        return clauseNames;
    }

    public SchemaComponent getSchema() {
        return schema;
    }

}
