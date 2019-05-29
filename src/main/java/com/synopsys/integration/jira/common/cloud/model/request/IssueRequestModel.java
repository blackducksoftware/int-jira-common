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
package com.synopsys.integration.jira.common.cloud.model.request;

import java.util.List;
import java.util.Map;

import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.cloud.model.IdComponent;
import com.synopsys.integration.jira.common.model.EntityProperty;

public class IssueRequestModel extends JiraRequestModel {
    private final IdComponent transition;
    private final Map<String, Object> fields;
    private final List<FieldUpdateOperationComponent> update;
    private final List<EntityProperty> properties;

    public IssueRequestModel(final IdComponent transition, final IssueRequestModelFieldsBuilder fieldsBuilder, final List<FieldUpdateOperationComponent> update, final List<EntityProperty> properties) {
        this.transition = transition;
        this.fields = fieldsBuilder.build();
        this.update = update;
        this.properties = properties;
    }

    public IdComponent getTransition() {
        return transition;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public List<FieldUpdateOperationComponent> getUpdate() {
        return update;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }

}
