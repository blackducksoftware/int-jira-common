/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class SchemaComponent extends IntRestComponent {
    private String type;
    private String system;

    public SchemaComponent() {
    }

    public SchemaComponent(final String type, final String system) {
        this.type = type;
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public String getSystem() {
        return system;
    }

}
