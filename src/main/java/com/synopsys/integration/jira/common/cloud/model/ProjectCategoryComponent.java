package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class ProjectCategoryComponent extends JiraComponent {
    private String self;
    private String id;
    private String name;
    private String description;

    public ProjectCategoryComponent() {
    }

    public ProjectCategoryComponent(final String self, final String id, final String name, final String description) {
        this.self = self;
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
