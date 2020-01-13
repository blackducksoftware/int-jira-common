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
package com.synopsys.integration.jira.common.cloud.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.request.WorkflowIssueTypeMappingsRequestModel;
import com.synopsys.integration.jira.common.model.request.WorkflowSchemeRequestModel;
import com.synopsys.integration.jira.common.model.response.IssueTypesWorkflowMappingResponseModel;
import com.synopsys.integration.jira.common.model.response.WorkflowSchemeResponseModel;
import com.synopsys.integration.jira.common.rest.service.JiraService;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;

public class WorkflowSchemeService {
    public static final String API_PATH = "/rest/api/2/workflowscheme";

    private JiraService jiraCloudService;

    public WorkflowSchemeService(JiraService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public WorkflowSchemeResponseModel updateScheme(WorkflowSchemeRequestModel requestModel) throws IntegrationException {
        return jiraCloudService.put(requestModel, createApiUri(), WorkflowSchemeResponseModel.class);
    }

    public WorkflowSchemeResponseModel getSchemeById(String schemeId) throws IntegrationException {
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(createSchemeApiUri(schemeId));
        return jiraCloudService.get(request, WorkflowSchemeResponseModel.class);
    }

    public IssueTypesWorkflowMappingResponseModel getIssueTypesForWorkflowInScheme(WorkflowIssueTypeMappingsRequestModel requestModel) throws IntegrationException {
        String uri = createWorkflowApiUri(requestModel.getId());
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(uri);
        return jiraCloudService.get(request, IssueTypesWorkflowMappingResponseModel.class);
    }

    public WorkflowSchemeResponseModel setIssueTypesForWorkflowInScheme(WorkflowIssueTypeMappingsRequestModel requestModel) throws IntegrationException {
        String uri = createWorkflowApiUri(requestModel.getId());
        return jiraCloudService.put(requestModel, uri, WorkflowSchemeResponseModel.class);
    }

    public void deleteIssueTypesForWorkflowInScheme(String schemeId) throws IntegrationException {
        Response response = jiraCloudService.delete(createWorkflowApiUri(schemeId));

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error deleting issue type mappings; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

    private String createSchemeApiUri(String schemeId) {
        return String.format("%s/%s", createApiUri(), schemeId);
    }

    private String createWorkflowApiUri(String schemeId) {
        return String.format("%s/workflow", createSchemeApiUri(schemeId));
    }
}
