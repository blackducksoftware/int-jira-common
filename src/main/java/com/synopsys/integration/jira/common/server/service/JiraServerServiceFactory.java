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

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.IssuePropertyService;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.rest.service.JiraService;
import com.synopsys.integration.jira.common.rest.service.PluginManagerService;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.service.IntJsonTransformer;
import com.synopsys.integration.rest.service.IntResponseTransformer;

public class JiraServerServiceFactory {
    private final IntLogger logger;
    private final JiraHttpClient httpClient;
    private final Gson gson;
    private final IntResponseTransformer responseTransformer;
    private final IntJsonTransformer jsonTransformer;

    public JiraServerServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson) {
        this.logger = logger;
        this.httpClient = httpClient;
        this.gson = gson;
        this.jsonTransformer = new IntJsonTransformer(gson, logger);
        this.responseTransformer = new IntResponseTransformer(httpClient, jsonTransformer);
    }

    public JiraService createJiraService() {
        return new JiraService(gson, httpClient, responseTransformer, jsonTransformer);
    }

    public ProjectService createProjectService() {
        return new ProjectService(createJiraService());
    }

    public IssueService createIssueService() {
        return new IssueService(jsonTransformer, createJiraService(), createUserSearchService(), createProjectService(), createIssueTypeService());
    }

    public IssuePropertyService createIssuePropertyService() {
        return new IssuePropertyService(gson, createJiraService());
    }

    public IssueSearchService createIssueSearchService() {
        return new IssueSearchService(createJiraService());
    }

    public IssueTypeService createIssueTypeService() {
        return new IssueTypeService(createJiraService());
    }

    public UserSearchService createUserSearchService() {
        return new UserSearchService(createJiraService());
    }

    public PluginManagerService createPluginManagerService() {
        return new PluginManagerService(gson, httpClient, createJiraService());
    }

    public IntLogger getLogger() {
        return logger;
    }

    public JiraHttpClient getHttpClient() {
        return httpClient;
    }

    public Gson getGson() {
        return gson;
    }
}
