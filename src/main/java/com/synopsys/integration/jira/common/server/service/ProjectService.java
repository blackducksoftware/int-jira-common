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
package com.synopsys.integration.jira.common.server.service;

import java.util.List;
import java.util.stream.Collectors;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.rest.JiraService;
import com.synopsys.integration.rest.request.Request;

public class ProjectService {
    public static final String API_PATH = "/rest/api/2/project";
    private JiraService jiraService;

    public ProjectService(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    public List<ProjectComponent> getProjects() throws IntegrationException {
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraService.getList(request, ProjectComponent.class);
    }

    public List<ProjectComponent> getProjectsByName(String projectName) throws IntegrationException {
        // TODO gavink - I could not find a way to query for projects through the API, so we may have issues handling large collections of results.
        return getProjects()
                   .stream()
                   .filter(project -> project.getName().contains(projectName))
                   .collect(Collectors.toList());
    }

    private String createApiUri() {
        return jiraService.getBaseUrl() + API_PATH;
    }

}
