package com.synopsys.integration.jira.common.cloud.enumeration;

public enum QueryValidationStrategy {
    STRICT,
    WARN,
    NONE,
    TRUE,
    FALSE;

    @Override
    public String toString() {
        final String name = this.name();
        return name.toLowerCase();
    }

}
