/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class SchemaComponent extends IntRestComponent {
    private String type;
    private String system;
    private String items;

    public SchemaComponent() {
    }

    public SchemaComponent(String type, String system, String items) {
        this.type = type;
        this.system = system;
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public String getSystem() {
        return system;
    }

    public String getItems() {
        return items;
    }

}
