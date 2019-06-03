package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.rest.component.IntRestResponse;

public class StatusCategory extends IntRestResponse {
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
