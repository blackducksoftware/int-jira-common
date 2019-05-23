package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class ChangeDetailsComponent extends JiraComponent {
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
