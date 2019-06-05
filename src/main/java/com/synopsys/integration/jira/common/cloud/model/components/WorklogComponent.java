/**
 * int-jira-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.jira.common.cloud.model.components;

import java.util.List;

import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.rest.component.IntRestComponent;

public class WorklogComponent extends IntRestComponent {
    private String self;
    private String id;
    private String issueId;
    private UserDetailsComponent author;
    private UserDetailsComponent updateAuthor;
    private String comment;
    private String created;
    private String updated;
    private VisibilityComponent visibility;
    private String started;
    private String timeSpent;
    private Integer timeSpentSeconds;
    private List<EntityProperty> properties;

    public WorklogComponent() {
    }

    public WorklogComponent(final String self, final String id, final String issueId, final UserDetailsComponent author, final UserDetailsComponent updateAuthor, final String comment, final String created, final String updated,
        final VisibilityComponent visibility, final String started, final String timeSpent, final Integer timeSpentSeconds, final List<EntityProperty> properties) {
        this.self = self;
        this.id = id;
        this.issueId = issueId;
        this.author = author;
        this.updateAuthor = updateAuthor;
        this.comment = comment;
        this.created = created;
        this.updated = updated;
        this.visibility = visibility;
        this.started = started;
        this.timeSpent = timeSpent;
        this.timeSpentSeconds = timeSpentSeconds;
        this.properties = properties;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getIssueId() {
        return issueId;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public UserDetailsComponent getUpdateAuthor() {
        return updateAuthor;
    }

    public String getComment() {
        return comment;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public VisibilityComponent getVisibility() {
        return visibility;
    }

    public String getStarted() {
        return started;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }
}
