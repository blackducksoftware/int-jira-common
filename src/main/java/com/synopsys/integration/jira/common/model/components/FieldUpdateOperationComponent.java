/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import java.util.Map;

import com.synopsys.integration.rest.component.IntRestComponent;

public class FieldUpdateOperationComponent extends IntRestComponent {
    private Map<String, Object> add;
    private Map<String, Object> set;
    private Map<String, Object> remove;
    private Map<String, Object> edit;

    public FieldUpdateOperationComponent() {
    }

    public FieldUpdateOperationComponent(final Map<String, Object> add, final Map<String, Object> set, final Map<String, Object> remove, final Map<String, Object> edit) {
        this.add = add;
        this.set = set;
        this.remove = remove;
        this.edit = edit;
    }

    public Map<String, Object> getAdd() {
        return add;
    }

    public Map<String, Object> getSet() {
        return set;
    }

    public Map<String, Object> getRemove() {
        return remove;
    }

    public Map<String, Object> getEdit() {
        return edit;
    }

}
