/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import java.util.List;

import com.blackduck.integration.rest.component.IntRestComponent;

public class IssueIncludedFieldsComponent extends IntRestComponent {
    private List<String> included;
    private List<String> excluded;
    private List<String> actuallyIncluded;

    public IssueIncludedFieldsComponent() {
    }

    public IssueIncludedFieldsComponent(final List<String> included, final List<String> excluded, final List<String> actuallyIncluded) {
        this.included = included;
        this.excluded = excluded;
        this.actuallyIncluded = actuallyIncluded;
    }

    public List<String> getIncluded() {
        return included;
    }

    public List<String> getExcluded() {
        return excluded;
    }

    public List<String> getActuallyIncluded() {
        return actuallyIncluded;
    }

}
