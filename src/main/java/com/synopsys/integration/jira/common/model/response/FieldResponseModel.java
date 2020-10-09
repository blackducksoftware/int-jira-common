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

import com.google.gson.JsonElement;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class FieldResponseModel extends JiraResponseModel {
    private boolean required;
    private JsonElement schema;
    private String name;
    private String key;
    private boolean hasDefaultValue;
    private List<String> operations;
    private List<JsonElement> allowedValues;

    public FieldResponseModel() {
    }

    public FieldResponseModel(boolean required,
        JsonElement schema,
        String name,
        String key,
        boolean hasDefaultValue,
        List<String> operations,
        List<JsonElement> allowedValues) {
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

    public JsonElement getSchema() {
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
