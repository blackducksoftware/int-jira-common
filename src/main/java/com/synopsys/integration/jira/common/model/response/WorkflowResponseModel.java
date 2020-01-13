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

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraResponse;

public class WorkflowResponseModel extends JiraResponse {
    private String name;
    private String description;
    private String lastModifiedDate;
    private String lastModifiedUser;
    private String lastModifiedUserAccountId;
    private Integer steps;
    @SerializedName("default")
    private Boolean isDefault;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public String getLastModifiedUserAccountId() {
        return lastModifiedUserAccountId;
    }

    public Integer getSteps() {
        return steps;
    }

    public Boolean getDefault() {
        return isDefault;
    }

}
