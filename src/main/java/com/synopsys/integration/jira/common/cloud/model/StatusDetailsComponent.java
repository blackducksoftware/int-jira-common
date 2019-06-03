package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.rest.component.IntRestResponse;

public class StatusDetailsComponent extends IntRestResponse {
    private String self;
    private String description;
    private String iconUrl;
    private String name;
    private String id;
    private StatusCategory statusCategory;

    public StatusDetailsComponent() {
    }

    public StatusDetailsComponent(final String self, final String description, final String iconUrl, final String name, final String id, final StatusCategory statusCategory) {
        this.self = self;
        this.description = description;
        this.iconUrl = iconUrl;
        this.name = name;
        this.id = id;
        this.statusCategory = statusCategory;
    }

    public String getSelf() {
        return self;
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public StatusCategory getStatusCategory() {
        return statusCategory;
    }
}
