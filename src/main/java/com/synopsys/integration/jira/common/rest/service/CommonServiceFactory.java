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
package com.synopsys.integration.jira.common.rest.service;

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.service.IntJsonTransformer;
import com.synopsys.integration.rest.service.IntResponseTransformer;

public class CommonServiceFactory {
    private final JiraHttpClient httpClient;
    private final Gson gson;
    private final IntResponseTransformer responseTransformer;
    private final IntJsonTransformer jsonTransformer;

    public CommonServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.jsonTransformer = new IntJsonTransformer(gson, logger);
        this.responseTransformer = new IntResponseTransformer(httpClient, jsonTransformer);
    }

    public JiraService createJiraService() {
        return new JiraService(gson, httpClient, responseTransformer, jsonTransformer);
    }

    public IssuePropertyService createIssuePropertyService() {
        return new IssuePropertyService(gson, createJiraService());
    }

    public IssueTypeService createIssueTypeService() {
        return new IssueTypeService(createJiraService());
    }

    public PluginManagerService createPluginManagerService() {
        return new PluginManagerService(gson, createJiraService());
    }

    public IssueMetaDataService createIssueMetadataService() {
        return new IssueMetaDataService(createJiraService());
    }

    public JiraHttpClient getHttpClient() {
        return httpClient;
    }

    public Gson getGson() {
        return gson;
    }

    public IntResponseTransformer getResponseTransformer() {
        return responseTransformer;
    }

    public IntJsonTransformer getJsonTransformer() {
        return jsonTransformer;
    }
}
