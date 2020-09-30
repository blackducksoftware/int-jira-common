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

import com.synopsys.integration.rest.component.IntRestComponent;

public class PermissionModel extends IntRestComponent {
    private String id;
    private String key;
    private String name;
    private String type;
    private String description;
    private Boolean havePermission;

    public PermissionModel() {
    }

    public PermissionModel(String id, String key, String name, String type, String description, Boolean havePermission) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.type = type;
        this.description = description;
        this.havePermission = havePermission;
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

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getHavePermission() {
        return havePermission;
    }

}
