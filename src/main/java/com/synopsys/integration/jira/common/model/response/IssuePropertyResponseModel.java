/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.google.gson.JsonObject;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

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
