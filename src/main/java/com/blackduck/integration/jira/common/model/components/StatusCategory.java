/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.rest.component.IntRestComponent;

public class StatusCategory extends IntRestComponent {
    private String self;
    private Integer id;
    private String key;
    private String colorName;
    private String name;

    public StatusCategory() {
    }

    public StatusCategory(final String self, final Integer id, final String key, final String colorName, final String name) {
        this.self = self;
        this.id = id;
        this.key = key;
        this.colorName = colorName;
        this.name = name;
    }

    public String getSelf() {
        return self;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getColorName() {
        return colorName;
    }

    public String getName() {
        return name;
    }
}
