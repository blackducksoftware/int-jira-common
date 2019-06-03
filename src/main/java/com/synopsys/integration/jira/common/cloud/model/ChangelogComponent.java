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

import java.util.List;

import com.synopsys.integration.rest.component.IntRestResponse;

public class ChangelogComponent extends IntRestResponse {
    private String id;
    private UserDetailsComponent author;
    private String created;
    private List<ChangeDetailsComponent> items;
    private HistoryMetadataComponent historyMetadata;

    public ChangelogComponent() {
    }

    public ChangelogComponent(final String id, final UserDetailsComponent author, final String created, final List<ChangeDetailsComponent> items, final HistoryMetadataComponent historyMetadata) {
        this.id = id;
        this.author = author;
        this.created = created;
        this.items = items;
        this.historyMetadata = historyMetadata;
    }

    public String getId() {
        return id;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public List<ChangeDetailsComponent> getItems() {
        return items;
    }

    public HistoryMetadataComponent getHistoryMetadata() {
        return historyMetadata;
    }

}
