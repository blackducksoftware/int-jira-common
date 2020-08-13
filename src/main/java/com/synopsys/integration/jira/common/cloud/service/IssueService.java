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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.exception.JiraPreconditionNotMetException;
import com.synopsys.integration.jira.common.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.rest.service.JiraService;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.response.Response;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class IssueService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_TRANSITIONS_SUFFIX = "transitions";
    public static final String API_PATH_COMMENTS_SUFFIX = "comment";

    private final IntJsonTransformer intJsonTransformer;
    private final JiraService jiraCloudService;
    private final UserSearchService userSearchService;
    private final ProjectService projectService;
    private final IssueTypeService issueTypeService;

    public IssueService(IntJsonTransformer intJsonTransformer, JiraService jiraCloudService, UserSearchService userSearchService, ProjectService projectService, IssueTypeService issueTypeService) {
        this.intJsonTransformer = intJsonTransformer;
        this.jiraCloudService = jiraCloudService;
        this.userSearchService = userSearchService;
        this.projectService = projectService;
        this.issueTypeService = issueTypeService;
    }

    public IssueResponseModel createIssue(IssueCreationRequestModel requestModel) throws IntegrationException {
        String issueTypeName = requestModel.getIssueTypeName();
        String projectName = requestModel.getProjectName();
        String reporterEmail = requestModel.getReporterEmail();

        IssueTypeResponseModel foundIssueType = issueTypeService.getAllIssueTypes().stream()
                                                    .filter(issueType -> issueType.getName().equalsIgnoreCase(issueTypeName))
                                                    .findFirst()
                                                    .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Issue type not found; issue type %s", issueTypeName)));
        UserDetailsResponseModel foundUserDetails = userSearchService.findUser(reporterEmail).stream()
                                                        .findFirst()
                                                        .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Reporter user with email not found; email: %s", reporterEmail)));
        PageOfProjectsResponseModel pageOfProjects = projectService.getProjectsByName(projectName);
        ProjectComponent foundProject = pageOfProjects.getProjects().stream()
                                            .findFirst()
                                            .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Project not found; project name: %s", projectName)));

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.copyFields(requestModel.getFieldsBuilder());

        fieldsBuilder.setIssueType(foundIssueType.getId());
        fieldsBuilder.setReporterId(foundUserDetails.getAccountId());
        fieldsBuilder.setProject(foundProject.getId());

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        IssueRequestModel issueRequestModel = new IssueRequestModel(fieldsBuilder, update, requestModel.getProperties());
        return createIssue(issueRequestModel);
    }

    private IssueResponseModel createIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createApiUri());
        return jiraCloudService.post(requestModel, httpUrl, IssueResponseModel.class);
    }

    public void updateIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl updateUri = createApiIssueUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.put(requestModel, updateUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error updating issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public IssueResponseModel getIssue(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiIssueUri(issueIdOrKey);
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .url(url)
                              .addQueryParameter("properties", "*all")
                              .build();
        return jiraCloudService.get(request, IssueResponseModel.class);
    }

    public void deleteIssue(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiIssueUri(issueIdOrKey);
        jiraCloudService.delete(url);
    }

    public void transitionIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl transitionsUri = createApiTransitionsUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.post(requestModel, transitionsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error transitioning issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public TransitionsResponseModel getTransitions(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiTransitionsUri(issueIdOrKey);
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(url);
        return jiraCloudService.get(request, TransitionsResponseModel.class);
    }

    public void addComment(IssueCommentRequestModel requestModel) throws IntegrationException {
        HttpUrl commentsUri = createApiCommentsUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.post(requestModel, commentsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error commenting on issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public StatusDetailsComponent getStatus(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiIssueQueryUri(issueIdOrKey, "status");
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(url);
        IssueResponseModel issueResponseModel = jiraCloudService.get(request, IssueResponseModel.class);
        String json = issueResponseModel.getJson();

        JsonObject issueObject = issueResponseModel.getJsonElement().getAsJsonObject();
        if (!issueObject.has("fields")) {
            throw new IntegrationException(String.format("The fields are missing from the IssueComponent. %s", json));
        }
        JsonObject fieldsObject = issueObject.getAsJsonObject("fields");
        if (!fieldsObject.has("status")) {
            throw new IntegrationException(String.format("The status is missing from the fields in the IssueComponent. %s", json));
        }
        JsonObject statusObject = fieldsObject.getAsJsonObject("status");
        StatusDetailsComponent statusDetailsComponent = intJsonTransformer.getComponentAs(statusObject, StatusDetailsComponent.class);

        return statusDetailsComponent;
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

    private HttpUrl createApiIssueUri(String issueIdOrKey) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s", createApiUri(), issueIdOrKey));
    }

    private HttpUrl createApiIssueQueryUri(String issueIdOrKey, String queryField) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s?fields=%s", createApiUri(), issueIdOrKey, queryField));
    }

    private HttpUrl createApiTransitionsUri(String issueIdOrKey) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_TRANSITIONS_SUFFIX));
    }

    private HttpUrl createApiCommentsUri(String issueIdOrKey) throws IntegrationException {
        return new HttpUrl(String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_COMMENTS_SUFFIX));
    }

}
