/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import java.util.Collections;
import java.util.List;

public class PageOfIssueCommentsComponent extends JiraPagedComponent {
    private List<CommentComponent> comments;

    public PageOfIssueCommentsComponent() {
        comments = Collections.emptyList();
    }

    public PageOfIssueCommentsComponent(final List<CommentComponent> comments) {
        this.comments = comments;
    }

    public List<CommentComponent> getComments() {
        return comments;
    }
}
