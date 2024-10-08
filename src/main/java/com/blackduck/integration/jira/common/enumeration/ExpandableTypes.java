/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.enumeration;

import org.apache.commons.lang3.StringUtils;

import com.blackduck.integration.util.EnumUtils;

public enum ExpandableTypes {
    RENDERED_FIELDS,
    NAMES,
    SCHEMA,
    TRANSITIONS,
    OPERATIONS,
    EDITMETA,
    CHANGELOG,
    VERSIONED_REPRESENTATIONS;

    @Override
    public String toString() {
        final String capitalizedWithSpaces = EnumUtils.prettyPrint(this);
        final String withoutSpaces = StringUtils.remove(capitalizedWithSpaces, ' ');
        return withoutSpaces.substring(0, 1).toLowerCase() + withoutSpaces.substring(1);
    }

}
