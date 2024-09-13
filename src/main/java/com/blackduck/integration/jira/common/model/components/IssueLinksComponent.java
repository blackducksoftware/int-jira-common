/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.rest.component.IntRestComponent;

public class IssueLinksComponent extends IntRestComponent {
    private String id;
    private Object type; // TODO
    private Object outwardIssue; // TODO

    public IssueLinksComponent() {
    }

    public IssueLinksComponent(final String id, final Object type, final Object outwardIssue) {
        this.id = id;
        this.type = type;
        this.outwardIssue = outwardIssue;
    }

    public String getId() {
        return id;
    }

    public Object getType() {
        return type;
    }

    public Object getOutwardIssue() {
        return outwardIssue;
    }

}
