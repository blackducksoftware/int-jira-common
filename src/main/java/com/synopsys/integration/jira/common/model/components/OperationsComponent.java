/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import java.util.List;

import com.synopsys.integration.rest.component.IntRestComponent;

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
