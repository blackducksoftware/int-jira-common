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
package com.synopsys.integration.jira.common.model.request;

import java.util.List;
import java.util.Map;

import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.model.components.IdComponent;
import com.synopsys.integration.jira.common.model.request.builder.IssueRequestModelFieldsMapBuilder;

public class IssueRequestModel extends JiraRequestModel {
    private final String issueIdOrKey;
    private final IdComponent transition;
    private final Map<String, Object> fields;
    private final Map<String, List<FieldUpdateOperationComponent>> update;
    private final List<EntityProperty> properties;

    // used to create a new issue because the id and transition are optional
    public IssueRequestModel(IssueRequestModelFieldsMapBuilder fieldsBuilder, Map<String, List<FieldUpdateOperationComponent>> update, List<EntityProperty> properties) {
        this(null, null, fieldsBuilder, update, properties);
    }

    public IssueRequestModel(String issueIdOrKey, IdComponent transition, IssueRequestModelFieldsMapBuilder fieldsBuilder, Map<String, List<FieldUpdateOperationComponent>> update,
        List<EntityProperty> properties) {
        this.issueIdOrKey = issueIdOrKey;
        this.transition = transition;
        this.fields = fieldsBuilder.build();
        this.update = update;
        this.properties = properties;
    }

    public String getIssueIdOrKey() {
        return issueIdOrKey;
    }

    public IdComponent getTransition() {
        return transition;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public Map<String, List<FieldUpdateOperationComponent>> getUpdate() {
        return update;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }

}
