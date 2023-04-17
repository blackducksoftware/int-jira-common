/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.IssueCreateMetadataResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.ProjectIssueCreateMetadataResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.rest.HttpUrl;

public class IssueMetaDataService {
    public static final String API_PATH = "/rest/api/2/issue/createmeta";

    private final JiraApiClient jiraApiClient;

    public IssueMetaDataService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public IssueCreateMetadataResponseModel getCreateMetadata() throws IntegrationException {
        HttpUrl uri = createApiUri();
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(uri);
        return jiraApiClient.get(request, IssueCreateMetadataResponseModel.class);
    }

    public IssueCreateMetadataResponseModel getCreateMetadataProjectIssueTypeFields(String projectKeyOrId, String issueTypeId) throws IntegrationException {
        HttpUrl issueFieldsQueryUri = createIssueFieldsQueryUri(projectKeyOrId, issueTypeId);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(issueFieldsQueryUri);
        return jiraApiClient.get(request, IssueCreateMetadataResponseModel.class);
    }

    public boolean doesProjectContainIssueType(String projectName, String issueType) throws IntegrationException {
        IssueCreateMetadataResponseModel response = getCreateMetadata();

        ProjectIssueCreateMetadataResponseModel matchingProject = null;
        for (ProjectIssueCreateMetadataResponseModel project : response.getProjects()) {
            if (project.getKey().equals(projectName) || project.getName().equals(projectName)) {
                matchingProject = project;
            }
        }

        if (null == matchingProject) {
            return false;
        }

        for (IssueTypeResponseModel issueTypeResponseModel : matchingProject.getIssueTypes()) {
            if (issueTypeResponseModel.getName().equals(issueType)) {
                return true;
            }
        }
        return false;
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);
    }

    private HttpUrl createIssueFieldsQueryUri(String projectIdOrKey, String issueTypeId) throws IntegrationException {
        return new HttpUrl(String.format("%s?projectKeys=%s&expand=projects.issuetypes.fields", createApiUri(), projectIdOrKey, issueTypeId));
    }
}
