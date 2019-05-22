package com.synopsys.integration.jira.common.cloud.model.request;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class FieldRequestModel extends JiraComponent {
    private String name;
    private String description;
    private String type;
    private String searcherKey;

    public FieldRequestModel(final String name, final String description, final String type, final String searcherKey) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.searcherKey = searcherKey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSearcherKey() {
        return searcherKey;
    }

}
