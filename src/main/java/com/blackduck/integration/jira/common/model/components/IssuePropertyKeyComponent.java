/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.rest.component.IntRestComponent;

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
