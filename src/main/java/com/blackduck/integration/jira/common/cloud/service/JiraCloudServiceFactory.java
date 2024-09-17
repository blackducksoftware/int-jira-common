/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.service;

import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.service.CommonServiceFactory;
import com.blackduck.integration.log.IntLogger;
import com.google.gson.Gson;

public class JiraCloudServiceFactory extends CommonServiceFactory {

    public JiraCloudServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson) {
        super(logger, httpClient, gson);
    }

    public FieldService createFieldService() {
        return new FieldService(getJiraApiClient());
    }

    public IssueSearchService createIssueSearchService() {
        return new IssueSearchService(getJiraApiClient());
    }

    public IssueService createIssueService() {
        return new IssueService(getJsonTransformer(), getJiraApiClient(), createUserSearchService(), createProjectService(), createIssueTypeService());
    }

    public UserSearchService createUserSearchService() {
        return new UserSearchService(getJiraApiClient());
    }

    public WorkflowSchemeService createWorkflowSchemeService() {
        return new WorkflowSchemeService(getJiraApiClient());
    }

    public ProjectService createProjectService() {
        return new ProjectService(getJiraApiClient());
    }

    public MyPermissionsService createMyPermissionsService() {
        return new MyPermissionsService(getJiraApiClient());
    }

    public VersionService createVersionService() {
        return new VersionService(getJiraApiClient());
    }

}
