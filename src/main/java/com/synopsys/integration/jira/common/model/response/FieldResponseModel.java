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

import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.SchemaComponent;

public class FieldResponseModel extends JiraResponseModel {
    private String id;
    private String key;
    private String name;
    private Boolean custom;
    private Boolean navigable;
    private Boolean searchable;
    private List<String> clauseNames;
    private SchemaComponent schema;

    public FieldResponseModel() {
    }

    public FieldResponseModel(
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
