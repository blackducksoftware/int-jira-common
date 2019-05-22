package com.synopsys.integration.jira.common.cloud.model.response;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraComponent;
import com.synopsys.integration.jira.common.model.JiraResponse;

public class WorkflowResponseModel extends JiraResponse {
    private String name;
    private String description;
    private String lastModifiedDate;
    private String lastModifiedUser;
    private String lastModifiedUserAccountId;
    private Integer steps;
    @SerializedName("default")
    private Boolean isDefault;

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
