/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class ChangeDetailsComponent extends IntRestComponent {
    private String field;
    private String fieldtype;
    private String fieldId;
    private String from;
    private String fromString;
    private String to;
    private String toString;

    public ChangeDetailsComponent() {
    }

    public ChangeDetailsComponent(final String field, final String fieldtype, final String fieldId, final String from, final String fromString, final String to, final String toString) {
        this.field = field;
        this.fieldtype = fieldtype;
        this.fieldId = fieldId;
        this.from = from;
        this.fromString = fromString;
        this.to = to;
        this.toString = toString;
    }

    public String getField() {
        return field;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public String getFieldId() {
        return fieldId;
    }

    public String getFrom() {
        return from;
    }

    public String getFromString() {
        return fromString;
    }

    public String getTo() {
        return to;
    }

    public String getToString() {
        return toString;
    }

}
