/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.request;

import com.synopsys.integration.jira.common.enumeration.FieldType;

public class FieldRequestModel extends JiraRequestModel {
    private final String name;
    private final String description;
    private final String type;
    private final String searcherKey;

    public FieldRequestModel(final String name, final String description, final FieldType fieldType) {
        this.name = name;
        this.description = description;
        this.type = fieldType.getTypeKey();
        this.searcherKey = fieldType.getSearcherKey();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSearcherKey() {
        return searcherKey;
    }

}
