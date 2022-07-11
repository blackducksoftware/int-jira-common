/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.exception.JiraPreconditionNotMetException;
import com.synopsys.integration.jira.common.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.model.components.IdComponent;
import com.synopsys.integration.jira.common.model.components.IssueTypeScope;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.IssueCommentResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueCreationResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class IssueService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_TRANSITIONS_SUFFIX = "transitions";
    public static final String API_PATH_COMMENTS_SUFFIX = "comment";

    private static final String JSON_OBJECT_STATUS = "status";
    private static final String JSON_OBJECT_FIELDS = "fields";

    private final IntJsonTransformer intJsonTransformer;
    private final JiraApiClient jiraCloudService;
    private final UserSearchService userSearchService;
    private final ProjectService projectService;
    private final IssueTypeService issueTypeService;

    public IssueService(IntJsonTransformer intJsonTransformer, JiraApiClient jiraCloudService, UserSearchService userSearchService, ProjectService projectService, IssueTypeService issueTypeService) {
        this.intJsonTransformer = intJsonTransformer;
        this.jiraCloudService = jiraCloudService;
        this.userSearchService = userSearchService;
        this.projectService = projectService;
        this.issueTypeService = issueTypeService;
    }

    public IssueCreationResponseModel createIssue(IssueCreationRequestModel requestModel) throws IntegrationException {
        String issueTypeName = requestModel.getIssueTypeName();
        String projectName = requestModel.getProjectName();
        String reporterEmail = requestModel.getReporterEmail();

        PageOfProjectsResponseModel pageOfProjects = projectService.getProjectsByName(projectName);
        String projectId = pageOfProjects.getProjects()
                               .stream()
                               .findFirst()
                               .map(ProjectComponent::getId)
                               .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Project not found; project name: %s", projectName)));

        String issueTypeId = retrieveIssueTypeId(issueTypeName, projectId);

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.copyFields(requestModel.getFieldsBuilder());

        fieldsBuilder.setIssueType(issueTypeId);
        if (StringUtils.isNotBlank(reporterEmail)) {
            String accountId = retrieveUserAccountId(reporterEmail);
            fieldsBuilder.setReporterId(accountId);
        }
        fieldsBuilder.setProject(projectId);

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        IssueRequestModel issueRequestModel = new IssueRequestModel(fieldsBuilder, update, requestModel.getProperties());
        return createIssue(issueRequestModel);
    }

    private String retrieveIssueTypeId(String issueTypeName, String projectId) throws IntegrationException {
        List<IssueTypeResponseModel> allIssueTypes = issueTypeService.getAllIssueTypes();
        List<IssueTypeResponseModel> issueTypeCandidates = allIssueTypes
                                                               .stream()
                                                               .filter(issueType -> issueType.getName().equalsIgnoreCase(issueTypeName))
                                                               .collect(Collectors.toList());
        String issueTypeId = null;
        for (IssueTypeResponseModel issueTypeCandidate : issueTypeCandidates) {
            IssueTypeScope scope = issueTypeCandidate.getScope();
            if (null != scope) {
                IdComponent scopedProject = scope.getProject();
                if (null != scopedProject) {
                    if (projectId.equals(scopedProject.getId())) {
                        // Exact match. Search complete.
                        return issueTypeCandidate.getId();
                    }
                    // Wrong project. Ignore.
                } else if (null == issueTypeId) {
                    // No project scope. This could be a match if there is no unscoped issue type.
                    issueTypeId = issueTypeCandidate.getId();
                }
            } else {
                // No scope. This could be a match.
                issueTypeId = issueTypeCandidate.getId();
            }
        }

        if (null != issueTypeId) {
            return issueTypeId;
        }
        throw new JiraPreconditionNotMetException(String.format("Issue type not found; issue type %s", issueTypeName));
    }

    private String retrieveUserAccountId(@Nullable String reporterEmail) throws IntegrationException {
        return userSearchService.findUser(reporterEmail).stream()
                   .findFirst()
                   .map(UserDetailsResponseModel::getAccountId)
                   .orElseThrow(() -> new JiraPreconditionNotMetException(String.format("Reporter user with email not found; email: %s", reporterEmail)));
    }

    public IssueCreationResponseModel createIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createApiUri());
        return jiraCloudService.post(requestModel, httpUrl, IssueCreationResponseModel.class);
    }

    public void updateIssue(IssueRequestModel requestModel) throws IntegrationException {
        HttpUrl updateUri = createApiIssueUri(requestModel.getIssueIdOrKey());
        JiraResponse response = jiraCloudService.put(requestModel, updateUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error updating issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public IssueResponseModel getIssue(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiIssueUri(issueIdOrKey);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
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
        JiraResponse response = jiraCloudService.post(requestModel, transitionsUri);

        if (response.isStatusCodeError()) {
            throw new IntegrationException(String.format("Error transitioning issue; cause: (%d) - %s", response.getStatusCode(), response.getStatusMessage()));
        }
    }

    public TransitionsResponseModel getTransitions(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiTransitionsUri(issueIdOrKey);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(url);
        return jiraCloudService.get(request, TransitionsResponseModel.class);
    }

    public IssueCommentResponseModel addComment(IssueCommentRequestModel requestModel) throws IntegrationException {
        HttpUrl commentsUri = createApiCommentsUri(requestModel.getIssueIdOrKey());
        return jiraCloudService.post(requestModel, commentsUri, IssueCommentResponseModel.class);
    }

    public StatusDetailsComponent getStatus(String issueIdOrKey) throws IntegrationException {
        HttpUrl url = createApiIssueQueryUri(issueIdOrKey, JSON_OBJECT_STATUS);
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(url);
        IssueResponseModel issueResponseModel = jiraCloudService.get(request, IssueResponseModel.class);
        String json = issueResponseModel.getJson();

        JsonObject issueObject = issueResponseModel.getJsonElement().getAsJsonObject();
        if (!issueObject.has(JSON_OBJECT_FIELDS)) {
            throw new IntegrationException(String.format("The fields are missing from the IssueComponent. %s", json));
        }
        JsonObject fieldsObject = issueObject.getAsJsonObject(JSON_OBJECT_FIELDS);
        if (!fieldsObject.has(JSON_OBJECT_STATUS)) {
            throw new IntegrationException(String.format("The status is missing from the fields in the IssueComponent. %s", json));
        }
        JsonObject statusObject = fieldsObject.getAsJsonObject(JSON_OBJECT_STATUS);
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
