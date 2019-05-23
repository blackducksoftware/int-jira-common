package com.synopsys.integration.jira.common.cloud.enumeration;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.util.EnumUtils;

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
