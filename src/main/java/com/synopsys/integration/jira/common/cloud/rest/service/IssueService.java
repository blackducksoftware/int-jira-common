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
package com.synopsys.integration.jira.common.cloud.rest.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.IssueComponent;
import com.synopsys.integration.jira.common.cloud.model.request.IssueRequestModel;

public class IssueService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_TRANSITIONS_SUFFIX = "transitions";

    private JiraCloudService jiraCloudService;

    public IssueService(final JiraCloudService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public IssueComponent createIssue(final IssueRequestModel requestModel) throws IntegrationException {
        return jiraCloudService.post(requestModel, createApiUri(), IssueComponent.class);
    }

    public void updateIssue(final IssueRequestModel requestModel) throws IntegrationException {
        final String updateUri = createApiUpdateUri(requestModel.getIssueIdOrKey());
        jiraCloudService.put(requestModel, updateUri);
    }

    public void transitionIssue(final IssueRequestModel requestModel) throws IntegrationException {
        final String transitionsUri = createApiTransitionsUri(requestModel.getIssueIdOrKey());
        jiraCloudService.post(requestModel, transitionsUri);
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

    private String createApiUpdateUri(final String issueIdOrKey) {
        return String.format("%s/%s", jiraCloudService.getBaseUrl() + API_PATH, issueIdOrKey);
    }

    private String createApiTransitionsUri(final String issueIdOrKey) {
        return String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_TRANSITIONS_SUFFIX);
    }

}
