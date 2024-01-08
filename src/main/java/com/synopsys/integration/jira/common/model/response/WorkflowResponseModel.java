/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class WorkflowResponseModel extends JiraResponseModel {
    private String name;
    private String description;
    private String lastModifiedDate;
    private String lastModifiedUser;
    private String lastModifiedUserAccountId;
    private Integer steps;
    @SerializedName("default")
    private Boolean isDefault;

    public WorkflowResponseModel() {
    }

    public WorkflowResponseModel(
        String name,
        String description,
        String lastModifiedDate,
        String lastModifiedUser,
        String lastModifiedUserAccountId,
        Integer steps,
        Boolean isDefault
    ) {
        this.name = name;
        this.description = description;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedUser = lastModifiedUser;
        this.lastModifiedUserAccountId = lastModifiedUserAccountId;
        this.steps = steps;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public String getLastModifiedUserAccountId() {
        return lastModifiedUserAccountId;
    }

    public Integer getSteps() {
        return steps;
    }

    public Boolean getDefault() {
        return isDefault;
    }

}
