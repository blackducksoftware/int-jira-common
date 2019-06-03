package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class IssueFieldsComponent {

    private List<IssueAttachmentComponent> attachment;
    private PageOfIssueCommentsComponent comment;
    private String description;
    private List<IssueLinksComponent> issuelinks;
    private ProjectComponent project;
    @SerializedName("subtasks")
    private List<IssueLinksComponent> subTasks;
    private Object timetracking; // TODO
    private String updated;
    private WatcherComponent watcher;
    private PageOfWorklogsComponent worklog;

    public IssueFieldsComponent() {
    }

    public List<IssueAttachmentComponent> getAttachment() {
        return attachment;
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

    public Object getTimetracking() {
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
