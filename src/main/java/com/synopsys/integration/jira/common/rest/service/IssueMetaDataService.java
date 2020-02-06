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
package com.synopsys.integration.jira.common.rest.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.response.IssueCreateMetadataResponse;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.ProjectIssueCreateMetadataResponse;
import com.synopsys.integration.rest.request.Request;

public class IssueMetaDataService {
    public static final String API_PATH = "/rest/api/2/issue/createmeta";

    private JiraService jiraService;

    public IssueMetaDataService(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    public IssueCreateMetadataResponse getCreateMetadata() throws IntegrationException {
        String uri = createApiUri();
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(uri);
        return jiraService.get(request, IssueCreateMetadataResponse.class);
    }

    public boolean doesProjectContainIssueType(String projectName, String issueType) throws IntegrationException {
        IssueCreateMetadataResponse response = getCreateMetadata();

        ProjectIssueCreateMetadataResponse matchingProject = null;
        for (ProjectIssueCreateMetadataResponse project : response.getProjects()) {
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

    private String createApiUri() {
        return jiraService.getBaseUrl() + API_PATH;
    }
}
