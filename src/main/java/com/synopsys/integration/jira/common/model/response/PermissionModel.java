/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.synopsys.integration.rest.component.IntRestComponent;

public class PermissionModel extends IntRestComponent {
    private String id;
    private String key;
    private String name;
    private String type;
    private String description;
    private Boolean havePermission;

    public PermissionModel() {
    }

    public PermissionModel(String id, String key, String name, String type, String description, Boolean havePermission) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.type = type;
        this.description = description;
        this.havePermission = havePermission;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getHavePermission() {
        return havePermission;
    }

}
