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

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

public class ProjectService {
    public static final String API_PATH_PROJECT = "/rest/api/2/project";
    public static final String API_PATH_SEARCH = API_PATH_PROJECT + "/search";
    private final JiraApiClient jiraApiClient;

    public ProjectService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
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

    private HttpUrl createSearchUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_SEARCH);
    }

    private HttpUrl createPathApiUri(String pathVariable) throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_PROJECT).appendRelativeUrl(pathVariable);
    }

}
