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
