package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.components.IssueFieldsComponent;

public interface IssueResponseModel {
    String getId();
    String getKey();
    String getSelf();
    IssueFieldsComponent getFields();
}
