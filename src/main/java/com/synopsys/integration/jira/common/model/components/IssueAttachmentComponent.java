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
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class IssueAttachmentComponent extends IntRestComponent {
    private Integer id;
    private String self;
    private String fileName;
    private UserDetailsComponent author;
    private String created;
    private Integer size;
    private String mimeType;
    private String content;
    private String thumbnail;

    public IssueAttachmentComponent() {
    }

    public IssueAttachmentComponent(final Integer id, final String self, final String fileName, final UserDetailsComponent author, final String created, final Integer size, final String mimeType, final String content,
        final String thumbnail) {
        this.id = id;
        this.self = self;
        this.fileName = fileName;
        this.author = author;
        this.created = created;
        this.size = size;
        this.mimeType = mimeType;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public Integer getId() {
        return id;
    }

    public String getSelf() {
        return self;
    }

    public String getFileName() {
        return fileName;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public Integer getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}
