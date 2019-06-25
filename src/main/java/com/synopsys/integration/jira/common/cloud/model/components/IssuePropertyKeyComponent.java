package com.synopsys.integration.jira.common.cloud.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class IssuePropertyKeyComponent extends IntRestComponent {
    private String self;
    private String key;

    public IssuePropertyKeyComponent() {
    }

    public IssuePropertyKeyComponent(final String self, final String key) {
        this.self = self;
        this.key = key;
    }

    public String getSelf() {
        return self;
    }

    public String getKey() {
        return key;
    }

}
