package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueSearchIssueFieldsComponent extends JiraComponent {
    private List<IssueAttachmentComponent> attachment;
    private List<CommentComponent> comment;
    private String description;
    private List<Object> issuelinks; // TODO
    private ProjectComponent project;
    @SerializedName("sub-tasks")
    private List<Object> subTasks; // TODO
    private Object timetracking; // TODO
    private Integer updated;
    private Object watcher; // TODO
    private List<Object> worklog; // TODO

    public IssueSearchIssueFieldsComponent() {
    }

}
