/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
    private String summary;
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
        String summary,
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
        this.summary = summary;
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

    public String getSummary() {
        return summary;
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
