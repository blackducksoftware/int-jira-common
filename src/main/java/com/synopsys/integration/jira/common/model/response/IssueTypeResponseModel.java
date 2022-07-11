/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.IssueTypeScope;
import com.synopsys.integration.rest.component.IntRestComponent;

public class IssueTypeResponseModel extends JiraResponseModel {
    private String self;
    private String id;
    private String description;
    private String iconUrl;
    private String name;
    private Boolean subtask;
    private Integer avatarId;
    private Map<String, JsonElement> fields;
    private @Nullable IssueTypeScope scope;

    public IssueTypeResponseModel() {
    }

    public IssueTypeResponseModel(
        String self,
        String id,
        String description,
        String iconUrl,
        String name,
        Boolean subtask,
        Integer avatarId,
        Map<String, JsonElement> fields,
        @Nullable IssueTypeScope scope
    ) {
        this.self = self;
        this.id = id;
        this.description = description;
        this.iconUrl = iconUrl;
        this.name = name;
        this.subtask = subtask;
        this.avatarId = avatarId;
        this.fields = fields;
        this.scope = scope;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        return name;
    }

    public Boolean getSubtask() {
        return subtask;
    }

    public Integer getAvatarId() {
        return avatarId;
    }

    public Map<String, JsonElement> getFields() {
        return fields;
    }

    public @Nullable IssueTypeScope getScope() {
        return scope;
    }

    public Map<String, IssueCreatemetaFieldResponseModel> getTypedFields(Gson gson) {
        return getFields().entrySet()
                   .stream()
                   .filter(entry -> !IntRestComponent.FIELD_NAME_JSON.equals(entry.getKey()))
                   .collect(Collectors.toMap(Map.Entry::getKey, entry -> extractValues(gson, entry.getValue())));
    }

    private IssueCreatemetaFieldResponseModel extractValues(Gson gson, JsonElement jsonElement) {
        return gson.fromJson(jsonElement, IssueCreatemetaFieldResponseModel.class);
    }

}
