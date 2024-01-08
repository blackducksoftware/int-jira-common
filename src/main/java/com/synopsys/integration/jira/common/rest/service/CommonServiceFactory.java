/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.service;

import com.google.gson.Gson;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.service.IntJsonTransformer;

public class CommonServiceFactory {
    private final JiraHttpClient httpClient;
    private final Gson gson;
    private final IntJsonTransformer jsonTransformer;
    private final JiraApiClient jiraApiClient;

    public CommonServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.jsonTransformer = new IntJsonTransformer(gson, logger);

        jiraApiClient = new JiraApiClient(gson, httpClient, jsonTransformer);
    }

    public IssuePropertyService createIssuePropertyService() {
        return new IssuePropertyService(gson, getJiraApiClient());
    }

    public IssueTypeService createIssueTypeService() {
        return new IssueTypeService(getJiraApiClient());
    }

    public PluginManagerService createPluginManagerService() {
        return new PluginManagerService(gson, getJiraApiClient());
    }

    public IssueMetaDataService createIssueMetadataService() {
        return new IssueMetaDataService(getJiraApiClient());
    }

    public JiraApiClient getJiraApiClient() {
        return jiraApiClient;
    }

    public JiraHttpClient getHttpClient() {
        return httpClient;
    }

    public Gson getGson() {
        return gson;
    }

    public IntJsonTransformer getJsonTransformer() {
        return jsonTransformer;
    }
}
