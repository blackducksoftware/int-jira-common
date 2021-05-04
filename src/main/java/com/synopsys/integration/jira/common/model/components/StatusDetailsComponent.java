/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class StatusDetailsComponent extends IntRestComponent {
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
