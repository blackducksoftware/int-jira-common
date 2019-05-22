package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class SchemaComponent extends JiraComponent {
    private String type;
    private String system;

    public SchemaComponent(final String type, final String system) {
        this.type = type;
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public String getSystem() {
        return system;
    }

}
