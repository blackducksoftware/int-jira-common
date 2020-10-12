/**
 * int-jira-common
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.jira.common.server.model;

import com.synopsys.integration.jira.common.model.request.JiraRequestModel;

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
