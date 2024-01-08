/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
