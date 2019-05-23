package com.synopsys.integration.jira.common.cloud.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.model.IssueSearchIssueComponent;
import com.synopsys.integration.jira.common.cloud.model.SchemaComponent;
import com.synopsys.integration.jira.common.model.JiraPagedResponseModel;

public class IssueSearchResponseModel extends JiraPagedResponseModel {
    private String expand;
    private List<IssueSearchIssueComponent> issues;
    private List<String> warningMessages;
    private Object names;
    private SchemaComponent schema;

    public String getExpand() {
        return expand;
    }

    public List<IssueSearchIssueComponent> getIssues() {
        return issues;
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    // TODO create a bean for this object
    public Object getNames() {
        return names;
    }

    public SchemaComponent getSchema() {
        return schema;
    }

}
