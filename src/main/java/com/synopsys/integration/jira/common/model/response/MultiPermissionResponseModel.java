/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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

    public MultiPermissionResponseModel() {
    }

    public MultiPermissionResponseModel(JsonObject permissions) {
        this.permissions = permissions;
    }

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
