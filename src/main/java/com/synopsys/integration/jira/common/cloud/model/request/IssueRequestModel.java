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
import com.synopsys.integration.jira.common.cloud.model.EntityProperty;
import com.synopsys.integration.jira.common.cloud.model.FieldUpdateOperation;
import com.synopsys.integration.jira.common.cloud.model.TransitionComponent;
import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueRequestModel extends JiraComponent {
    private final TransitionComponent transition;
    private final Map<String, Object> fields;
    private final List<FieldUpdateOperation> update;
    private final List<EntityProperty> properties;

    public IssueRequestModel(final TransitionComponent transition, final IssueRequestModelFieldsBuilder fieldsBuilder, final List<FieldUpdateOperation> update, final List<EntityProperty> properties) {
        this.transition = transition;
        this.fields = fieldsBuilder.build();
        this.update = update;
        this.properties = properties;
    }

    public TransitionComponent getTransition() {
        return transition;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public List<FieldUpdateOperation> getUpdate() {
        return update;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }

}
