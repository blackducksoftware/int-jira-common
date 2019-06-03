package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class PageOfIssueCommentsComponent extends JiraPageResponseModel {
    private List<CommentComponent> comments;

    public PageOfIssueCommentsComponent() {
    }

    public PageOfIssueCommentsComponent(final List<CommentComponent> comments) {
        this.comments = comments;
    }

    public List<CommentComponent> getComments() {
        return comments;
    }
}
