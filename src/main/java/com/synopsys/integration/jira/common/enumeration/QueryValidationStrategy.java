/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.enumeration;

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
