/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.components.ProjectComponent;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.blackduck.integration.jira.common.model.response.VersionResponseModel;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.rest.HttpUrl;

public class ProjectService {
    public static final String API_PATH_PROJECT = "/rest/api/3/project";
    public static final String API_PATH_SEARCH = API_PATH_PROJECT + "/search";
    public static final String API_PATH_VERSIONS = "/versions";
    private final JiraApiClient jiraApiClient;

    public ProjectService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public PageOfProjectsResponseModel getProjects(int limit, int offset) throws IntegrationException {
        JiraRequest.Builder requestBuilder = JiraRequestFactory.createDefaultPageRequestBuilder(limit, offset);
        requestBuilder.url(createSearchUri());
        return jiraApiClient.get(requestBuilder.build(), PageOfProjectsResponseModel.class);
    }

    public PageOfProjectsResponseModel getProjects() throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createSearchUri());
        return jiraApiClient.get(request, PageOfProjectsResponseModel.class);
    }

    public ProjectComponent getProject(String keyOrId) throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(createPathApiUri(keyOrId))
                                  .build();
        return jiraApiClient.get(request, ProjectComponent.class);
    }

    public PageOfProjectsResponseModel getProjectsByName(String projectName) throws IntegrationException {
        if (StringUtils.isBlank(projectName)) {
            return new PageOfProjectsResponseModel();
        }

        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(createSearchUri())
                                  .addQueryParameter("query", projectName)
                                  .addQueryParameter("orderBy", "name")
                                  .build();
        return jiraApiClient.get(request, PageOfProjectsResponseModel.class);
    }

    public List<VersionResponseModel> getProjectVersions(String projectKeyOrId) throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createVersionsApiUri(projectKeyOrId));
        return jiraApiClient.getList(request, VersionResponseModel.class);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_PROJECT);
    }

    private HttpUrl createSearchUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_SEARCH);
    }

    private HttpUrl createPathApiUri(String pathVariable) throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_PROJECT).appendRelativeUrl(pathVariable);
    }

    private HttpUrl createVersionsApiUri(String projectKeyOrId) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s%s", createApiUri(), projectKeyOrId, API_PATH_VERSIONS));
    }

}
