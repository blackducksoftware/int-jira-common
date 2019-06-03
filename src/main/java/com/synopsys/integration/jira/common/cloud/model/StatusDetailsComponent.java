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

public class StatusDetailsComponent extends IntRestResponse {
    private String self;
    private String description;
    private String iconUrl;
    private String name;
    private String id;
    private StatusCategory statusCategory;

    public StatusDetailsComponent() {
    }

    public StatusDetailsComponent(final String self, final String description, final String iconUrl, final String name, final String id, final StatusCategory statusCategory) {
        this.self = self;
        this.description = description;
        this.iconUrl = iconUrl;
        this.name = name;
        this.id = id;
        this.statusCategory = statusCategory;
    }

    public String getSelf() {
        return self;
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public StatusCategory getStatusCategory() {
        return statusCategory;
    }
}
