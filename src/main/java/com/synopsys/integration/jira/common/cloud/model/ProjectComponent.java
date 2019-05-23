package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class ProjectComponent extends JiraComponent {
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

    public ProjectComponent(final String self, final String id, final String key, final String name, final AvatarUrlsComponent avatarUrls, final ProjectCategoryComponent projectCategory, final Boolean simplified, final String style) {
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
