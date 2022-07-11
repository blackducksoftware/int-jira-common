/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class IssueCommentResponseModel extends JiraResponseModel {
    private String self;
    private String id;
    private IssueCommentAuthorResponseModel author;
    private String body;
    private IssueCommentAuthorResponseModel updateAuthor;

    public IssueCommentResponseModel() {
    }

    public IssueCommentResponseModel(String self, String id, IssueCommentAuthorResponseModel author, String body, IssueCommentAuthorResponseModel updateAuthor) {
        this.self = self;
        this.id = id;
        this.author = author;
        this.body = body;
        this.updateAuthor = updateAuthor;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public IssueCommentAuthorResponseModel getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public IssueCommentAuthorResponseModel getUpdateAuthor() {
        return updateAuthor;
    }
}
