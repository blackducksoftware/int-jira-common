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
