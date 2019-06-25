package com.synopsys.integration.jira.common.cloud.model.response;

import com.google.gson.JsonObject;
import com.synopsys.integration.jira.common.model.JiraResponse;

public class IssuePropertyResponseModel extends JiraResponse {
    private String key;
    private JsonObject value;

    public IssuePropertyResponseModel() {
    }

    public String getKey() {
        return key;
    }

    public JsonObject getValue() {
        return value;
    }

}
