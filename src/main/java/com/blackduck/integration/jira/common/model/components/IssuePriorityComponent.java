/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.rest.component.IntRestComponent;

public class IssuePriorityComponent extends IntRestComponent {
    private String self;
    private String iconUrl;
    private String name;
    private String id;

    public IssuePriorityComponent() {
    }

    public IssuePriorityComponent(String self, String iconUrl, String name, String id) {
        this.self = self;
        this.iconUrl = iconUrl;
        this.name = name;
        this.id = id;
    }

    public String getSelf() {
        return self;
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

}
