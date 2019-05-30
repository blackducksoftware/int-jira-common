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

public class ProjectComponent extends IntRestResponse {
    private String self;
    private String id;
    private String key;
    private String name;
    private AvatarUrlsComponent avatarUrls;
    private ProjectCategoryComponent projectCategory;
    private Boolean simplified;
    private String style;

    public ProjectComponent() {
    }

    public ProjectComponent(final String self, final String id, final String key, final String name, final AvatarUrlsComponent avatarUrls, final ProjectCategoryComponent projectCategory, final Boolean simplified, final String style) {
        this.self = self;
        this.id = id;
        this.key = key;
        this.name = name;
        this.avatarUrls = avatarUrls;
        this.projectCategory = projectCategory;
        this.simplified = simplified;
        this.style = style;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public AvatarUrlsComponent getAvatarUrls() {
        return avatarUrls;
    }

    public ProjectCategoryComponent getProjectCategory() {
        return projectCategory;
    }

    public Boolean getSimplified() {
        return simplified;
    }

    public String getStyle() {
        return style;
    }

}
