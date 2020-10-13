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
package com.synopsys.integration.jira.common.server.service;

import java.util.List;
import java.util.stream.Collectors;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.VersionResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

public class ProjectService {
    public static final String API_PATH = "/rest/api/2/project";
    public static final String API_PATH_VERSIONS = "/versions";

    private final JiraApiClient jiraApiClient;

    public ProjectService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public ProjectComponent getProject(String keyOrId) throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(createPathApiUri(keyOrId))
                                  .build();
        return jiraApiClient.get(request, ProjectComponent.class);
    }

    public List<ProjectComponent> getProjects() throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraApiClient.getList(request, ProjectComponent.class);
    }

    public List<ProjectComponent> getProjectsByName(String projectName) throws IntegrationException {
        // TODO gavink - I could not find a way to query for projects through the API, so we may have issues handling large collections of results.
        return getProjects()
                   .stream()
                   .filter(project -> project.getName().equals(projectName) || project.getKey().equals(projectName))
                   .collect(Collectors.toList());
    }

    public List<VersionResponseModel> getProjectVersions(String projectKeyOrId) throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createVersionsApiUri(projectKeyOrId));
        return jiraApiClient.getList(request, VersionResponseModel.class);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);
    }

    private HttpUrl createPathApiUri(String pathVariable) throws IntegrationException {
        return createApiUri().appendRelativeUrl(pathVariable);
    }

    private HttpUrl createVersionsApiUri(String projectKeyOrId) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s%s", createApiUri(), projectKeyOrId, API_PATH_VERSIONS));
    }

}
