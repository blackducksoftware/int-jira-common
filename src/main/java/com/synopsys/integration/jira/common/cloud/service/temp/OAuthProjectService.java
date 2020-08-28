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
package com.synopsys.integration.jira.common.cloud.service.temp;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.oauth.http.JiraHttpService;
import com.synopsys.integration.rest.HttpUrl;

public class OAuthProjectService {
    public static final String API_PATH = "/rest/api/2/project/search";
    private final JiraHttpService jiraCloudService;

    public OAuthProjectService(JiraHttpService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public PageOfProjectsResponseModel getProjects() throws IntegrationException {
        return jiraCloudService.get(API_PATH, PageOfProjectsResponseModel.class);
    }

    public PageOfProjectsResponseModel getProjectsByName(String projectName) throws IntegrationException {
        if (StringUtils.isBlank(projectName)) {
            return new PageOfProjectsResponseModel();
        }

        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(new HttpUrl(API_PATH))
                                  .addQueryParameter("query", projectName)
                                  .addQueryParameter("orderBy", "name")
                                  .build();
        return jiraCloudService.get(request.getUrl().string(), PageOfProjectsResponseModel.class);
    }

}
