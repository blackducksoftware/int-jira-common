/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.JiraResponseModel;
import com.google.gson.JsonObject;

public class IssuePropertyResponseModel extends JiraResponseModel {
    private String key;
    private JsonObject value;

    public IssuePropertyResponseModel() {
    }

    public IssuePropertyResponseModel(String key, JsonObject value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public JsonObject getValue() {
        return value;
    }

}
