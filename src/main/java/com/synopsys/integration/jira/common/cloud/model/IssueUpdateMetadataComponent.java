package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueUpdateMetadataComponent extends JiraComponent {
    private Object fields;

    public IssueUpdateMetadataComponent() {
    }

    public IssueUpdateMetadataComponent(final Object fields) {
        this.fields = fields;
    }

    public Object getFields() {
        return fields;
    }

}
