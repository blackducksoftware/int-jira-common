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
package com.synopsys.integration.jira.common.model.components;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.rest.component.IntRestComponent;

public class IssueFieldsComponent extends IntRestComponent {
    private List<IssueAttachmentComponent> attachment;
    private UserDetailsComponent assignee;
    private PageOfIssueCommentsComponent comment;
    private String description;
    private List<IssueLinksComponent> issuelinks;
    private ProjectComponent project;
    @SerializedName(value = "subtasks", alternate = "sub-tasks")
    private List<IssueLinksComponent> subTasks;
    private JsonElement timetracking; // TODO
    private String updated;
    private WatcherComponent watcher;
    private PageOfWorklogsComponent worklog;

    public IssueFieldsComponent() {
    }

    public IssueFieldsComponent(
        List<IssueAttachmentComponent> attachment,
        UserDetailsComponent assignee,
        PageOfIssueCommentsComponent comment,
        String description,
        List<IssueLinksComponent> issuelinks,
        ProjectComponent project,
        List<IssueLinksComponent> subTasks,
        JsonElement timetracking,
        String updated,
        WatcherComponent watcher,
        PageOfWorklogsComponent worklog
    ) {
        this.attachment = attachment;
        this.assignee = assignee;
        this.comment = comment;
        this.description = description;
        this.issuelinks = issuelinks;
        this.project = project;
        this.subTasks = subTasks;
        this.timetracking = timetracking;
        this.updated = updated;
        this.watcher = watcher;
        this.worklog = worklog;
    }

    public List<IssueAttachmentComponent> getAttachment() {
        return attachment;
    }

    public UserDetailsComponent getAssignee() {
        return assignee;
    }

    public PageOfIssueCommentsComponent getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }

    public List<IssueLinksComponent> getIssuelinks() {
        return issuelinks;
    }

    public ProjectComponent getProject() {
        return project;
    }

    public List<IssueLinksComponent> getSubTasks() {
        return subTasks;
    }

    public JsonElement getTimetracking() {
        return timetracking;
    }

    public String getUpdated() {
        return updated;
    }

    public WatcherComponent getWatcher() {
        return watcher;
    }

    public PageOfWorklogsComponent getWorklog() {
        return worklog;
    }
}
