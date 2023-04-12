/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class ProjectComponent extends JiraResponseModel {
    private String self;
    private String id;
    private String key;
    private String name;
    private AvatarUrlsComponent avatarUrls;
    private ProjectCategoryComponent projectCategory;
    private Boolean simplified;
    private String style;

    public ProjectComponent() {
    }

    public ProjectComponent(String self, String id, String key, String name, AvatarUrlsComponent avatarUrls, ProjectCategoryComponent projectCategory, Boolean simplified, String style) {
        this.self = self;
        this.id = id;
        this.key = key;
        this.name = name;
        this.avatarUrls = avatarUrls;
        this.projectCategory = projectCategory;
        this.simplified = simplified;
        this.style = style;
    }

    public String getSelf() {
        return self;
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

    public AvatarUrlsComponent getAvatarUrls() {
        return avatarUrls;
    }

    public ProjectCategoryComponent getProjectCategory() {
        return projectCategory;
    }

    public Boolean getSimplified() {
        return simplified;
    }

    public String getStyle() {
        return style;
    }

}
