/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.model;

import java.util.List;

import com.blackduck.integration.jira.common.model.JiraPageResponseModel;

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
