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
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class MultiPermissionResponseModel extends JiraResponseModel {
    private JsonObject permissions;

    public Map<String, PermissionModel> extractPermissions() {
        return permissions.keySet()
                   .stream()
                   .filter(this::isPermissionKey)
                   .map(this::extractPermission)
                   .collect(Collectors.toMap(PermissionModel::getKey, Function.identity()));
    }

    public PermissionModel extractPermission(String permission) {
        if (permissions.has(permission)) {
            JsonElement permissionCandidate = permissions.get(permission);
            if (permissionCandidate.isJsonObject()) {
                return getGson().fromJson(permissionCandidate, PermissionModel.class);
            }
        }
        return null;
    }

    private boolean isPermissionKey(String jsonMemberKey) {
        return StringUtils.containsOnly(jsonMemberKey, "_ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

}
