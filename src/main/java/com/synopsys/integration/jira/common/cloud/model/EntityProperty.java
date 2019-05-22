package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class EntityProperty extends JiraComponent {
    private String key;
    private Object value;

    public EntityProperty(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

}
