/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.JiraResponseModel;

public class VersionResponseModel extends JiraResponseModel {
    private String self;
    private String id;
    private String description;
    private String name;
    private boolean archived;
    private boolean released;
    private String releaseDate;
    private boolean overdue;
    private String userReleaseDate;
    private String projectId;

    public VersionResponseModel() {
    }

    public VersionResponseModel(
        String self,
        String id,
        String description,
        String name,
        boolean archived,
        boolean released,
        String releaseDate,
        boolean overdue,
        String userReleaseDate,
        String projectId
    ) {
        this.self = self;
        this.id = id;
        this.description = description;
        this.name = name;
        this.archived = archived;
        this.released = released;
        this.releaseDate = releaseDate;
        this.overdue = overdue;
        this.userReleaseDate = userReleaseDate;
        this.projectId = projectId;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isReleased() {
        return released;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public String getUserReleaseDate() {
        return userReleaseDate;
    }

    public String getProjectId() {
        return projectId;
    }
}
