/**
 * int-jira-common
 * <p>
 * Copyright (c) 2020 Synopsys, Inc.
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.jira.common.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.exception.JiraPreconditionNotMetException;
import com.synopsys.integration.jira.common.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.request.builder.IssueRequestModelFieldsMapBuilder;
import com.synopsys.integration.jira.common.model.response.IssueCommentResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.server.model.IssueCreationRequestModel;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class IssueService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_TRANSITIONS_SUFFIX = "transitions";
    public static final String API_PATH_COMMENTS_SUFFIX = "comment";

    private static final String JSON_OBJECT_STATUS = "status";
    private static final String JSON_OBJECT_FIELDS = "fields";

    private final IntJsonTransformer intJsonTransformer;
    private final JiraApiClient jiraApiClient;
    private final UserSearchService userSearchService;
    private final ProjectService projectService;
    private final IssueTypeService issueTypeService;

    public IssueService(IntJsonTransformer intJsonTransformer, JiraApiClient jiraApiClient, UserSearchService userSearchService, ProjectService projectService, IssueTypeService issueTypeService) {
        this.intJsonTransformer = intJsonTransformer;
        this.jiraApiClient = jiraApiClient;
        this.userSearchService = userSearchService;
        this.projectService = projectService;
        this.issueTypeService = issueTypeService;
    }

    // FIXME Both server and cloud return an object that has far too many fields for the actual data that is returned.
    public IssueResponseModel createIssue(IssueCreationRequestModel requestModel) throws IntegrationException {
        String issueTypeName = requestModel.getIssueTypeName();
        String projectName = requestModel.getProjectName();
        String reporter = requestModel.getReporterUsername();

        IssueTypeResponseModel foundIssueType = issueTypeService.getAllIssueTypes().stream()
            .filter(issueType -> issueType.getName().equalsIgnoreCase(issueTypeName))
            .findFirst()
            .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Issue type not found; issue type %s", issueTypeName)));
        UserDetailsResponseModel foundUserDetails = userSearchService.findUserByUsername(reporter)
            .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Reporter user with email not found; email: %s", reporter)));
        List<ProjectComponent> projects = projectService.getProjectsByName(projectName);
        ProjectComponent foundProject = projects.stream()
            .findFirst()
            .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Project not found; project name: %s", projectName)));

        return createIssue(foundIssueType.getId(), foundUserDetails.getName(), foundProject.getId(), requestModel.getFieldsBuilder());
    }

    public IssueResponseModel createIssue(String issueTypeId, String reporterUserName, String projectId, IssueRequestModelFieldsMapBuilder issueRequestModelFieldsMapBuilder) throws IntegrationException {

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.copyFields(issueRequestModelFieldsMapBuilder);

        fieldsBuilder.setIssueType(issueTypeId);
        fieldsBuilder.setReporterName(reporterUserName);
        fieldsBuilder.setProject(projectId);

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        IssueRequestModel issueRequestModel = new IssueRequestModel(fieldsBuilder, update, new ArrayList<>());
        return createIssue(issueRequestModel);
    }

    private IssueResponseModel createIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createApiUri());
        return jiraApiClient.post(requestModel, httpUrl, IssueResponseModel.class);
    }

    public void updateIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl updateUri = createApiIssueUri(requestModel.getIssueIdOrKey());
        JiraResponse response = jiraApiClient.put(requestModel, updateUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error updating issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public IssueResponseModel getIssue(String issueIdOrKey) throws IntegrationException {
        HttpUrl uri = createApiIssueUri(issueIdOrKey);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
            .url(uri)
            .addQueryParameter("properties", "*all")
            .build();
        return jiraApiClient.get(request, IssueResponseModel.class);
    }

    public void deleteIssue(String issueIdOrKey) throws IntegrationException {
        HttpUrl uri = createApiIssueUri(issueIdOrKey);
        jiraApiClient.delete(uri);
    }

    public void transitionIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl transitionsUri = createApiTransitionsUri(requestModel.getIssueIdOrKey());
        JiraResponse response = jiraApiClient.post(requestModel, transitionsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error transitioning issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public TransitionsResponseModel getTransitions(String issueIdOrKey) throws IntegrationException {
        HttpUrl uri = createApiTransitionsUri(issueIdOrKey);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(uri);
        return jiraApiClient.get(request, TransitionsResponseModel.class);
    }

    public IssueCommentResponseModel addComment(IssueCommentRequestModel requestModel) throws IntegrationException {
        HttpUrl commentsUri = createApiCommentsUri(requestModel.getIssueIdOrKey());
        return jiraApiClient.post(requestModel, commentsUri, IssueCommentResponseModel.class);
    }

    public StatusDetailsComponent getStatus(String issueIdOrKey) throws IntegrationException {
        HttpUrl uri = createApiIssueQueryUri(issueIdOrKey, JSON_OBJECT_STATUS);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(uri);
        IssueResponseModel issueResponseModel = jiraApiClient.get(request, IssueResponseModel.class);
        String json = issueResponseModel.getJson();

        JsonObject issueObject = issueResponseModel.getJsonElement().getAsJsonObject();
        if (!issueObject.has(JSON_OBJECT_FIELDS)) {
            throw new IntegrationException(String.format("The fields are missing from the IssueResponseModel. %s", json));
        }
        JsonObject fieldsObject = issueObject.getAsJsonObject(JSON_OBJECT_FIELDS);
        if (!fieldsObject.has(JSON_OBJECT_STATUS)) {
            throw new IntegrationException(String.format("The status is missing from the fields in the IssueResponseModel. %s", json));
        }
        JsonObject statusObject = fieldsObject.getAsJsonObject(JSON_OBJECT_STATUS);
        StatusDetailsComponent statusDetailsComponent = intJsonTransformer.getComponentAs(statusObject, StatusDetailsComponent.class);

        return statusDetailsComponent;
    }

    public JiraResponse getIssueFields(String projectIdOrKey, String issueTypeId) throws IntegrationException {
        HttpUrl issueFieldsQueryUri = createIssueFieldsQueryUri(projectIdOrKey, issueTypeId);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(issueFieldsQueryUri);
        JiraResponse jiraResponse = jiraApiClient.get(request);
        return jiraResponse;
    }

    private String createApiUri() {
        return jiraApiClient.getBaseUrl() + API_PATH;
    }

    private HttpUrl createIssueFieldsQueryUri(String projectIdOrKey, String issueTypeId) throws IntegrationException {
        //        return new HttpUrl(String.format("%s/createmeta?projectKeys=%s&expand=projects.issuetypes.fields", createApiUri(), projectIdOrKey, issueTypeId));
        return new HttpUrl(String.format("%s/createmeta/%s/issuetypes/%s", createApiUri(), projectIdOrKey, issueTypeId));
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
