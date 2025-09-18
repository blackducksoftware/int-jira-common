/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.service;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.response.IssueCreateMetadataResponseModel;
import com.blackduck.integration.jira.common.model.response.IssueTypeResponseModel;
import com.blackduck.integration.jira.common.model.response.ProjectIssueCreateMetadataResponseModel;
import com.blackduck.integration.jira.common.rest.RestApiVersion;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.rest.HttpUrl;

public class IssueMetaDataService {
    public static final String API_PATH = "/rest/api/2/issue/createmeta";

    private final JiraApiClient jiraApiClient;
    private final RestApiVersion restApiVersion;

    public IssueMetaDataService(JiraApiClient jiraApiClient, RestApiVersion restApiVersion) {
        this.jiraApiClient = jiraApiClient;
        this.restApiVersion = restApiVersion;
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
        return new HttpUrl(jiraApiClient.getBaseUrl() + "/rest/api/" + restApiVersion.getVersion() + "/issue/createmeta");
    }

    private HttpUrl createIssueFieldsQueryUri(String projectIdOrKey, String issueTypeId) throws IntegrationException {
        return new HttpUrl(String.format("%s?projectKeys=%s&expand=projects.issuetypes.fields", createApiUri(), projectIdOrKey, issueTypeId));
    }
}
