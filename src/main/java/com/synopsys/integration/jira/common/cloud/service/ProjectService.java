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
package com.synopsys.integration.jira.common.cloud.service;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraService;
import com.synopsys.integration.rest.request.Request;

public class ProjectService {
    public static final String API_PATH = "/rest/api/2/project/search";
    private JiraService jiraCloudService;

    public ProjectService(JiraService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public PageOfProjectsResponseModel getProjects() throws IntegrationException {
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraCloudService.get(request, PageOfProjectsResponseModel.class);
    }

    public PageOfProjectsResponseModel getProjectsByName(String projectName) throws IntegrationException {
        if (StringUtils.isBlank(projectName)) {
            return new PageOfProjectsResponseModel();
        }

        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(createApiUri())
                              .addQueryParameter("query", projectName)
                              .addQueryParameter("orderBy", "name")
                              .build();
        return jiraCloudService.get(request, PageOfProjectsResponseModel.class);
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

}
