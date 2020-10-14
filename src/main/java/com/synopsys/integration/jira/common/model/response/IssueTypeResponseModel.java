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

import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class IssueTypeResponseModel extends JiraResponseModel {
    private String self;
    private String id;
    private String description;
    private String iconUrl;
    private String name;
    private Boolean subtask;
    private Integer avatarId;
    private Map<String, JsonElement> fields;

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
        Map<String, JsonElement> fields
    ) {
        this.self = self;
        this.id = id;
        this.description = description;
        this.iconUrl = iconUrl;
        this.name = name;
        this.subtask = subtask;
        this.avatarId = avatarId;
        this.fields = fields;
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

    public Map<String, IssueCreatemetaFieldResponseModel> getTypedFields(Gson gson) {
        return getFields().entrySet()
                   .stream()
                   .filter(entry -> entry.getKey() != "json")
                   .collect(Collectors.toMap(Map.Entry::getKey, entry -> extractValues(gson, entry.getValue())));
    }

    private IssueCreatemetaFieldResponseModel extractValues(Gson gson, JsonElement jsonElement) {
        return gson.fromJson(jsonElement, IssueCreatemetaFieldResponseModel.class);
    }
}
