/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import java.util.List;

import com.blackduck.integration.rest.component.IntRestComponent;

public class OperationsComponent extends IntRestComponent {
    private List<Object> linkGroup;

    public OperationsComponent() {
    }

    public OperationsComponent(final List<Object> linkGroup) {
        this.linkGroup = linkGroup;
    }

    public List<Object> getLinkGroup() {
        return linkGroup;
    }

}
