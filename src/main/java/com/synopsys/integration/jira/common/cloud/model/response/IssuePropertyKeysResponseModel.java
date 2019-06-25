package com.synopsys.integration.jira.common.cloud.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.model.components.IssuePropertyKeyComponent;
import com.synopsys.integration.jira.common.model.JiraResponse;

public class IssuePropertyKeysResponseModel extends JiraResponse {
    private List<IssuePropertyKeyComponent> keys;

    public IssuePropertyKeysResponseModel() {
    }

    public List<IssuePropertyKeyComponent> getKeys() {
        return keys;
    }

}
