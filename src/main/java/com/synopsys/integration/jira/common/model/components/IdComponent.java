/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class IdComponent extends IntRestComponent {
    private String id;

    public IdComponent() {
    }

    public IdComponent(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
