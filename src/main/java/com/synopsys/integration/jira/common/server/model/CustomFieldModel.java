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
package com.synopsys.integration.jira.common.server.model;

import com.synopsys.integration.util.Stringable;

public class CustomFieldModel extends Stringable {
    private String id;
    private String name;
    private String type;
    private String searcherKey;
    private String self;
    private Integer numericId;
    private Boolean isLocked;
    private Boolean isManaged;
    private Boolean isAllProjects;
    private Integer projectsCount;
    private Integer screensCount;

    public CustomFieldModel() {
        // For serialization
    }

    public CustomFieldModel(String id, String name, String type, String searcherKey, String self, Integer numericId, Boolean isLocked, Boolean isManaged, Boolean isAllProjects, Integer projectsCount, Integer screensCount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.searcherKey = searcherKey;
        this.self = self;
        this.numericId = numericId;
        this.isLocked = isLocked;
        this.isManaged = isManaged;
        this.isAllProjects = isAllProjects;
        this.projectsCount = projectsCount;
        this.screensCount = screensCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSearcherKey() {
        return searcherKey;
    }

    public String getSelf() {
        return self;
    }

    public Integer getNumericId() {
        return numericId;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public Boolean getManaged() {
        return isManaged;
    }

    public Boolean getAllProjects() {
        return isAllProjects;
    }

    public Integer getProjectsCount() {
        return projectsCount;
    }

    public Integer getScreensCount() {
        return screensCount;
    }

}
