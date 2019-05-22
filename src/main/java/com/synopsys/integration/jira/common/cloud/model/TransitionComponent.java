package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class TransitionComponent extends JiraComponent {
    private String id;

    public TransitionComponent(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
