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
