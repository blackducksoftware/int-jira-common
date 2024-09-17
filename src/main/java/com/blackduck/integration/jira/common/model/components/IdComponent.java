/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.rest.component.IntRestComponent;

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
