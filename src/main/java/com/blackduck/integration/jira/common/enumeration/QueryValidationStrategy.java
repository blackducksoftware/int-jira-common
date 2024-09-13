/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.enumeration;

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
