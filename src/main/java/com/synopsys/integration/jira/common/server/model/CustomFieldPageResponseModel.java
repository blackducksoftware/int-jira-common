package com.synopsys.integration.jira.common.server.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class CustomFieldPageResponseModel extends JiraPageResponseModel {
    private Boolean isLast;
    private List<CustomFieldModel> values;

    public CustomFieldPageResponseModel(Boolean isLast, List<CustomFieldModel> values) {
        this.isLast = isLast;
        this.values = values;
    }

    public Boolean getLast() {
        return isLast;
    }

    public List<CustomFieldModel> getValues() {
        return values;
    }

}