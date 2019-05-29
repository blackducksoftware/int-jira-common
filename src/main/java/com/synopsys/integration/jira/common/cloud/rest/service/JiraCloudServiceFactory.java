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

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.cloud.rest.JiraCloudHttpClient;
import com.synopsys.integration.log.IntLogger;

public class JiraCloudServiceFactory {
    private final IntLogger logger;
    private final JiraCloudHttpClient httpClient;
    private final Gson gson;

    public JiraCloudServiceFactory(final IntLogger logger, final JiraCloudHttpClient httpClient, final Gson gson) {
        this.logger = logger;
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public FieldService createFieldService() {
        return new FieldService();
    }

    public IssueSearchService createIssueSearchService() {
        return new IssueSearchService();
    }

    public IssueService createIssueService() {
        return new IssueService();
    }

    public WorkflowSchemeService createWorkflowSchemeService() {
        return new WorkflowSchemeService();
    }

    public IntLogger getLogger() {
        return logger;
    }

    public JiraCloudHttpClient getHttpClient() {
        return httpClient;
    }

    public Gson getGson() {
        return gson;
    }
}
