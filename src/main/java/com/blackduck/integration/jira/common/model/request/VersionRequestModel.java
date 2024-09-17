/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.request;

public class VersionRequestModel extends JiraRequestModel {
    private String description;
    private String name;
    private boolean archived;
    private boolean released;
    private String releaseDate;
    private String userReleaseDate;
    private String project;
    private String projectId;

    public VersionRequestModel() {
    }

    public VersionRequestModel(String name, String projectId) {
        this.name = name;
        this.projectId = projectId;
    }

    public VersionRequestModel(
        String description,
        String name,
        boolean archived,
        boolean released,
        String releaseDate,
        String userReleaseDate,
        String project,
        String projectId
    ) {
        this.description = description;
        this.name = name;
        this.archived = archived;
        this.released = released;
        this.releaseDate = releaseDate;
        this.userReleaseDate = userReleaseDate;
        this.project = project;
        this.projectId = projectId;
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

    public String getUserReleaseDate() {
        return userReleaseDate;
    }

    public String getProject() {
        return project;
    }

    public String getProjectId() {
        return projectId;
    }
}
