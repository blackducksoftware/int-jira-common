/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import org.jetbrains.annotations.Nullable;

import com.blackduck.integration.rest.component.IntRestComponent;

public class IssueTypeScope extends IntRestComponent {
    private String type;
    private @Nullable IdComponent project;

    public IssueTypeScope() {
    }

    public IssueTypeScope(String type, @Nullable IdComponent project) {
        this.type = type;
        this.project = project;
    }

    public String getType() {
        return type;
    }

    public @Nullable IdComponent getProject() {
        return project;
    }

}
