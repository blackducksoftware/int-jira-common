/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.service;

import com.blackduck.integration.jira.common.rest.JiraHttpClient;
import com.blackduck.integration.jira.common.rest.RestApiVersion;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.rest.service.IntJsonTransformer;
import com.google.gson.Gson;

public class CommonServiceFactory {
    private final JiraHttpClient httpClient;
    private final Gson gson;
    private final IntJsonTransformer jsonTransformer;
    private final JiraApiClient jiraApiClient;
    private final RestApiVersion restApiVersion;

    public CommonServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson) {
        this(logger, httpClient, gson, RestApiVersion.VERSION_2);
    }

    public CommonServiceFactory(IntLogger logger, JiraHttpClient httpClient, Gson gson, RestApiVersion restApiVersion) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.restApiVersion = restApiVersion;
        this.jsonTransformer = new IntJsonTransformer(gson, logger);

        jiraApiClient = new JiraApiClient(gson, httpClient, jsonTransformer);
    }

    public IssuePropertyService createIssuePropertyService() {
        return new IssuePropertyService(gson, getJiraApiClient(), getRestApiVersion());
    }

    public IssueTypeService createIssueTypeService() {
        return new IssueTypeService(getJiraApiClient(), getRestApiVersion());
    }

    public PluginManagerService createPluginManagerService() {
        return new PluginManagerService(gson, getJiraApiClient());
    }

    public IssueMetaDataService createIssueMetadataService() {
        return new IssueMetaDataService(getJiraApiClient(), getRestApiVersion());
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

    public RestApiVersion getRestApiVersion() {
        return restApiVersion;
    }
}
