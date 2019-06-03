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
import com.synopsys.integration.jira.common.cloud.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.cloud.model.response.IssueResponseModel;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;

public class IssueService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_TRANSITIONS_SUFFIX = "transitions";
    public static final String API_PATH_COMMENTS_SUFFIX = "comment";

    private JiraCloudService jiraCloudService;

    public IssueService(final JiraCloudService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public IssueResponseModel createIssue(final IssueRequestModel requestModel) throws IntegrationException {
        return jiraCloudService.post(requestModel, createApiUri(), IssueResponseModel.class);
    }

    public void updateIssue(final IssueRequestModel requestModel) throws IntegrationException {
        final String updateUri = createApiUpdateUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.put(requestModel, updateUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error updating issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public IssueResponseModel getIssue(final String issueIdOrKey) throws IntegrationException {
        final String uri = createApiUpdateUri(issueIdOrKey);
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(uri);
        return jiraCloudService.get(request, IssueResponseModel.class);
    }

    public void deleteIssue(final String issueIdOrKey) throws IntegrationException {
        final String uri = createApiUpdateUri(issueIdOrKey);
        jiraCloudService.delete(uri);
    }

    public void transitionIssue(final IssueRequestModel requestModel) throws IntegrationException {
        final String transitionsUri = createApiTransitionsUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.post(requestModel, transitionsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error transitioning issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public void addComment(final IssueCommentRequestModel requestModel) throws IntegrationException {
        final String commentsUri = createApiCommentsUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.post(requestModel, commentsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error commenting on issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

    private String createApiUpdateUri(final String issueIdOrKey) {
        return String.format("%s/%s", createApiUri(), issueIdOrKey);
    }

    private String createApiTransitionsUri(final String issueIdOrKey) {
        return String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_TRANSITIONS_SUFFIX);
    }

    private String createApiCommentsUri(final String issueIdOrKey) {
        return String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_COMMENTS_SUFFIX);
    }

}
