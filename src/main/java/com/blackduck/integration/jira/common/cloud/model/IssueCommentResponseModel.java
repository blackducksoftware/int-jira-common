/*
 * int-jira-common
 *
 * Copyright (c) 2025 Black Duck Software, Inc. 
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.model;

import com.blackduck.integration.jira.common.model.JiraResponseModel;
import com.blackduck.integration.jira.common.model.response.IssueCommentAuthorResponseModel;

public class IssueCommentResponseModel extends JiraResponseModel {
    private String self;
    private String id;
    private IssueCommentAuthorResponseModel author;
    private AtlassianDocumentFormatModel body;
    private IssueCommentAuthorResponseModel updateAuthor;

    public IssueCommentResponseModel() {
    }

    public IssueCommentResponseModel(String self, String id, IssueCommentAuthorResponseModel author, AtlassianDocumentFormatModel body, IssueCommentAuthorResponseModel updateAuthor) {
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

    public AtlassianDocumentFormatModel getBody() {
        return body;
    }

    public IssueCommentAuthorResponseModel getUpdateAuthor() {
        return updateAuthor;
    }
}
