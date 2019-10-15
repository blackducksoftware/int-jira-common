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
package com.synopsys.integration.jira.common.server.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class IssueSearchResponseModel extends JiraPageResponseModel {
    private String expand;
    private List<IssueComponent> issues;

    public IssueSearchResponseModel() {
    }

    public IssueSearchResponseModel(String expand, List<IssueComponent> issues) {
        this.expand = expand;
        this.issues = issues;
    }

    public String getExpand() {
        return expand;
    }

    public List<String> getExpandAsList() {
        if (StringUtils.isNotBlank(expand)) {
            String[] expandedArray = expand.split(",");
            return Arrays.asList(expandedArray);
        }
        return Collections.emptyList();
    }

    public List<IssueComponent> getIssues() {
        return issues;
    }

}
