/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class IssueUpdateMetadataComponent extends IntRestComponent {
    private Object fields;

    public IssueUpdateMetadataComponent() {
    }

    public IssueUpdateMetadataComponent(final Object fields) {
        this.fields = fields;
    }

    public Object getFields() {
        return fields;
    }

}
