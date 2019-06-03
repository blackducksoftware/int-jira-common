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
package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.rest.component.IntRestResponse;

public class CommentComponent extends IntRestResponse {
    private String self;
    private String id;
    private UserDetailsComponent author;
    private String body;
    private UserDetailsComponent updateAuthor;
    private String created;
    private String updated;
    private VisibilityComponent visibility;

    public CommentComponent() {
    }

    public CommentComponent(final String self, final String id, final UserDetailsComponent author, final String body, final UserDetailsComponent updateAuthor, final String created, final String updated,
        final VisibilityComponent visibility) {
        this.self = self;
        this.id = id;
        this.author = author;
        this.body = body;
        this.updateAuthor = updateAuthor;
        this.created = created;
        this.updated = updated;
        this.visibility = visibility;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public UserDetailsComponent getUpdateAuthor() {
        return updateAuthor;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public Object getVisibility() {
        return visibility;
    }

}
