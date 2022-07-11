/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.request.WorkflowIssueTypeMappingsRequestModel;
import com.synopsys.integration.jira.common.model.request.WorkflowSchemeRequestModel;
import com.synopsys.integration.jira.common.model.response.IssueTypesWorkflowMappingResponseModel;
import com.synopsys.integration.jira.common.model.response.WorkflowSchemeResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

public class WorkflowSchemeService {
    public static final String API_PATH = "/rest/api/2/workflowscheme";

    private final JiraApiClient jiraCloudService;

    public WorkflowSchemeService(JiraApiClient jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public WorkflowSchemeResponseModel updateScheme(WorkflowSchemeRequestModel requestModel) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createApiUri());
        return jiraCloudService.put(requestModel, httpUrl, WorkflowSchemeResponseModel.class);
    }

    public WorkflowSchemeResponseModel getSchemeById(String schemeId) throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createSchemeApiUri(schemeId));
        return jiraCloudService.get(request, WorkflowSchemeResponseModel.class);
    }

    public IssueTypesWorkflowMappingResponseModel getIssueTypesForWorkflowInScheme(WorkflowIssueTypeMappingsRequestModel requestModel) throws IntegrationException {
        HttpUrl uri = createWorkflowApiUri(requestModel.getId());
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(uri);
        return jiraCloudService.get(request, IssueTypesWorkflowMappingResponseModel.class);
    }

    public WorkflowSchemeResponseModel setIssueTypesForWorkflowInScheme(WorkflowIssueTypeMappingsRequestModel requestModel) throws IntegrationException {
        HttpUrl uri = createWorkflowApiUri(requestModel.getId());
        return jiraCloudService.put(requestModel, uri, WorkflowSchemeResponseModel.class);
    }

    public void deleteIssueTypesForWorkflowInScheme(String schemeId) throws IntegrationException {
        JiraResponse response = jiraCloudService.delete(createWorkflowApiUri(schemeId));

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error deleting issue type mappings; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

    private HttpUrl createSchemeApiUri(String schemeId) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s", createApiUri(), schemeId));
    }

    private HttpUrl createWorkflowApiUri(String schemeId) throws IntegrationException {
        return new HttpUrl(String.format("%s/workflow", createSchemeApiUri(schemeId).string()));
    }
}
