/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.google.gson.JsonElement;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.components.SchemaComponent;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;

public class IssueSearchResponseModel extends JiraPageResponseModel {
    private String expand;
    private List<IssueResponseModel> issues;
    private List<String> warningMessages;
    private JsonElement names; // TODO create a bean for this object
    private SchemaComponent schema;

    public IssueSearchResponseModel() {
    }

    public IssueSearchResponseModel(String expand, List<IssueResponseModel> issues, List<String> warningMessages, JsonElement names, SchemaComponent schema) {
        this.expand = expand;
        this.issues = issues;
        this.warningMessages = warningMessages;
        this.names = names;
        this.schema = schema;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public List<IssueResponseModel> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueResponseModel> issues) {
        this.issues = issues;
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public JsonElement getNames() {
        return names;
    }

    public void setNames(JsonElement names) {
        this.names = names;
    }

    public SchemaComponent getSchema() {
        return schema;
    }

    public void setSchema(SchemaComponent schema) {
        this.schema = schema;
    }

}
