/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.exception.JiraPreconditionNotMetException;
import com.blackduck.integration.jira.common.model.components.FieldUpdateOperationComponent;
import com.blackduck.integration.jira.common.model.components.ProjectComponent;
import com.blackduck.integration.jira.common.model.components.StatusDetailsComponent;
import com.blackduck.integration.jira.common.server.model.IssueCommentRequestModel;
import com.blackduck.integration.jira.common.model.request.IssueRequestModel;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.request.builder.IssueRequestModelFieldsMapBuilder;
import com.blackduck.integration.jira.common.server.model.IssueCommentResponseModel;
import com.blackduck.integration.jira.common.model.response.IssueCreationResponseModel;
import com.blackduck.integration.jira.common.server.model.JiraServerIssueResponseModel;
import com.blackduck.integration.jira.common.model.response.IssueTypeResponseModel;
import com.blackduck.integration.jira.common.model.response.TransitionsResponseModel;
import com.blackduck.integration.jira.common.model.response.UserDetailsResponseModel;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.model.JiraResponse;
import com.blackduck.integration.jira.common.rest.service.IssueTypeService;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.blackduck.integration.jira.common.server.model.IssueCreationRequestModel;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.service.IntJsonTransformer;
import com.google.gson.JsonObject;

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

    public IssueCreationResponseModel createIssue(IssueCreationRequestModel requestModel) throws IntegrationException {
        String issueTypeName = StringUtils.trim(requestModel.getIssueTypeName());
        String projectName = StringUtils.trim(requestModel.getProjectName());
        String reporter = StringUtils.trim(requestModel.getReporterUsername());
        Predicate<ProjectComponent> nameMatchPredicate = issueTypeResponseModel -> issueTypeResponseModel.getName().equalsIgnoreCase(projectName);
        Predicate<ProjectComponent> keyMatchPredicate = issueTypeResponseModel -> issueTypeResponseModel.getKey().equalsIgnoreCase(projectName);

        IssueTypeResponseModel foundIssueType = issueTypeService.getAllIssueTypes().stream()
                                                    .filter(issueType -> issueType.getName().equalsIgnoreCase(issueTypeName))
                                                    .findFirst()
                                                    .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Issue type not found; issue type %s", issueTypeName)));
        String username = userSearchService.findUserByUsername(reporter)
                              .map(UserDetailsResponseModel::getName)
                              .orElse("");
        List<ProjectComponent> projects = projectService.getProjectsByName(projectName);
        ProjectComponent foundProject = projects.stream()
                                            .filter(nameMatchPredicate.or(keyMatchPredicate))
                                            .findFirst()
                                            .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Project not found; project name: %s", projectName)));

        return createIssue(foundIssueType.getId(), username, foundProject.getId(), requestModel.getFieldsBuilder());
    }

    public IssueCreationResponseModel createIssue(String issueTypeId, String reporterUserName, String projectId, IssueRequestModelFieldsMapBuilder issueRequestModelFieldsMapBuilder) throws IntegrationException {

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.copyFields(issueRequestModelFieldsMapBuilder);

        fieldsBuilder.setIssueType(issueTypeId);
        if (StringUtils.isNotBlank(reporterUserName)) {
            fieldsBuilder.setReporterName(reporterUserName);
        }
        fieldsBuilder.setProject(projectId);

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        IssueRequestModel issueRequestModel = new IssueRequestModel(fieldsBuilder, update, new ArrayList<>());
        return createIssue(issueRequestModel);
    }

    public IssueCreationResponseModel createIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createApiUri());
        return jiraApiClient.post(requestModel, httpUrl, IssueCreationResponseModel.class);
    }

    public void updateIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl updateUri = createApiIssueUri(requestModel.getIssueIdOrKey());
        JiraResponse response = jiraApiClient.put(requestModel, updateUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error updating issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public JiraServerIssueResponseModel getIssue(String issueIdOrKey) throws IntegrationException {
        HttpUrl uri = createApiIssueUri(issueIdOrKey);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(uri)
                                  .addQueryParameter("properties", "*all")
                                  .build();
        return jiraApiClient.get(request, JiraServerIssueResponseModel.class);
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
        JiraServerIssueResponseModel issueResponseModel = jiraApiClient.get(request, JiraServerIssueResponseModel.class);
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

    private String createApiUri() {
        return jiraApiClient.getBaseUrl() + API_PATH;
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
