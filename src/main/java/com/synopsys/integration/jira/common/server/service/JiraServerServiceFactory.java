/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.server.service;

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.CommonServiceFactory;
import com.synopsys.integration.log.IntLogger;

public class JiraServerServiceFactory extends CommonServiceFactory {

    public JiraServerServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson) {
        super(logger, httpClient, gson);
    }

    public CustomFieldService createCustomFieldService() {
        return new CustomFieldService(getJiraApiClient());
    }

    public FieldService createFieldService() {
        return new FieldService(getJiraApiClient());
    }

    public ProjectService createProjectService() {
        return new ProjectService(getJiraApiClient());
    }

    public IssueService createIssueService() {
        return new IssueService(getJsonTransformer(), getJiraApiClient(), createUserSearchService(), createProjectService(), createIssueTypeService());
    }

    public IssueSearchService createIssueSearchService() {
        return new IssueSearchService(getJiraApiClient());
    }

    public UserSearchService createUserSearchService() {
        return new UserSearchService(getJiraApiClient());
    }

    public MyPermissionsService createMyPermissionsService() {
        return new MyPermissionsService(getJiraApiClient());
    }

    public VersionService createVersionService() {
        return new VersionService(getJiraApiClient());
    }

}
