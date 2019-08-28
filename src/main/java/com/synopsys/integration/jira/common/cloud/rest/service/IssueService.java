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
package com.synopsys.integration.jira.common.cloud.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.cloud.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.cloud.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.cloud.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.UserDetailsResponseModel;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class IssueService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_TRANSITIONS_SUFFIX = "transitions";
    public static final String API_PATH_COMMENTS_SUFFIX = "comment";

    private final IntJsonTransformer intJsonTransformer;
    private final JiraCloudService jiraCloudService;
    private final UserSearchService userSearchService;
    private final ProjectService projectService;
    private final IssueTypeService issueTypeService;

    public IssueService(IntJsonTransformer intJsonTransformer, JiraCloudService jiraCloudService, UserSearchService userSearchService, ProjectService projectService, IssueTypeService issueTypeService) {
        this.intJsonTransformer = intJsonTransformer;
        this.jiraCloudService = jiraCloudService;
        this.userSearchService = userSearchService;
        this.projectService = projectService;
        this.issueTypeService = issueTypeService;
    }

    public IssueResponseModel createIssue(IssueCreationRequestModel requestModel) throws IntegrationException {
        final String issueTypeName = requestModel.getIssueTypeName();
        final String projectName = requestModel.getProjectName();
        final String reporterEmail = requestModel.getReporterEmail();

        IssueTypeResponseModel foundIssueType = issueTypeService.getAllIssueTypes().stream()
                                                    .filter(issueType -> issueType.getName().equalsIgnoreCase(issueTypeName))
                                                    .findFirst()
                                                    .orElseThrow(() -> new IllegalStateException(String.format("Issue type not found; issue type %s", issueTypeName)));
        UserDetailsResponseModel foundUserDetails = userSearchService.findUser(reporterEmail).stream()
                                                        .findFirst()
                                                        .orElseThrow(() -> new IllegalStateException(String.format("Reporter user with email not found; email: %s", reporterEmail)));
        PageOfProjectsResponseModel pageOfProjects = projectService.getProjectsByName(projectName);
        ProjectComponent foundProject = pageOfProjects.getProjects().stream()
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException(String.format("Project not found; project name: %s", projectName)));

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.copyFields(requestModel.getFieldsBuilder());

        fieldsBuilder.setIssueType(foundIssueType.getId());
        fieldsBuilder.setReporter(foundUserDetails.getAccountId());
        fieldsBuilder.setProject(foundProject.getId());

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        IssueRequestModel issueRequestModel = new IssueRequestModel(fieldsBuilder, update, requestModel.getProperties());
        return createIssue(issueRequestModel);
    }

    private IssueResponseModel createIssue(IssueRequestModel requestModel) throws IntegrationException {
        return jiraCloudService.post(requestModel, createApiUri(), IssueResponseModel.class);
    }

    public void updateIssue(IssueRequestModel requestModel) throws IntegrationException {
        final String updateUri = createApiIssueUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.put(requestModel, updateUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error updating issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public IssueResponseModel getIssue(String issueIdOrKey) throws IntegrationException {
        final String uri = createApiIssueUri(issueIdOrKey);
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(uri)
                              .addQueryParameter("properties", "*all")
                              .build();
        return jiraCloudService.get(request, IssueResponseModel.class);
    }

    public void deleteIssue(String issueIdOrKey) throws IntegrationException {
        final String uri = createApiIssueUri(issueIdOrKey);
        jiraCloudService.delete(uri);
    }

    public void transitionIssue(IssueRequestModel requestModel) throws IntegrationException {
        final String transitionsUri = createApiTransitionsUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.post(requestModel, transitionsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error transitioning issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public TransitionsResponseModel getTransitions(String issueIdOrKey) throws IntegrationException {
        final String uri = createApiTransitionsUri(issueIdOrKey);
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(uri);
        return jiraCloudService.get(request, TransitionsResponseModel.class);
    }

    public void addComment(IssueCommentRequestModel requestModel) throws IntegrationException {
        final String commentsUri = createApiCommentsUri(requestModel.getIssueIdOrKey());
        Response response = jiraCloudService.post(requestModel, commentsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error commenting on issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public StatusDetailsComponent getStatus(String issueIdOrKey) throws IntegrationException {
        final String uri = createApiIssueQueryUri(issueIdOrKey, "status");
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(uri);
        IssueResponseModel issueResponseModel = jiraCloudService.get(request, IssueResponseModel.class);
        String json = issueResponseModel.getJson();

        JsonObject issueObject = issueResponseModel.getJsonElement().getAsJsonObject();
        if (!issueObject.has("fields")) {
            throw new IntegrationException(String.format("The fields are missing from the IssueResponseModel. %s", json));
        }
        JsonObject fieldsObject = issueObject.getAsJsonObject("fields");
        if (!fieldsObject.has("status")) {
            throw new IntegrationException(String.format("The status is missing from the fields in the IssueResponseModel. %s", json));
        }
        JsonObject statusObject = fieldsObject.getAsJsonObject("status");
        StatusDetailsComponent statusDetailsComponent = intJsonTransformer.getComponentAs(statusObject, StatusDetailsComponent.class);

        return statusDetailsComponent;
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

    private String createApiIssueUri(String issueIdOrKey) {
        return String.format("%s/%s", createApiUri(), issueIdOrKey);
    }

    private String createApiIssueQueryUri(String issueIdOrKey, String queryField) {
        return String.format("%s/%s?fields=%s", createApiUri(), issueIdOrKey, queryField);
    }

    private String createApiTransitionsUri(String issueIdOrKey) {
        return String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_TRANSITIONS_SUFFIX);
    }

    private String createApiCommentsUri(String issueIdOrKey) {
        return String.format("%s/%s/%s", createApiUri(), issueIdOrKey, API_PATH_COMMENTS_SUFFIX);
    }

}
