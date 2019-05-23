package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class OperationsComponent extends JiraComponent {
    private List<Object> linkGroup;

    public OperationsComponent() {
    }

    public OperationsComponent(final List<Object> linkGroup) {
        this.linkGroup = linkGroup;
    }

    public List<Object> getLinkGroup() {
        return linkGroup;
    }

}
