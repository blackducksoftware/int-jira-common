/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.service;

import java.io.Serializable;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.response.IssuePropertyKeysResponseModel;
import com.blackduck.integration.jira.common.model.response.IssuePropertyResponseModel;
import com.blackduck.integration.jira.common.rest.RestApiVersion;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.rest.HttpUrl;
import com.google.gson.Gson;

public class IssuePropertyService {
    public static final String API_PATH = "/rest/api/2/issue";
    public static final String API_PATH_PROPERTIES_PIECE = "/properties";

    private final Gson gson;
    private final JiraApiClient jiraApiClient;
    private final RestApiVersion restApiVersion;

    public IssuePropertyService(Gson gson, JiraApiClient jiraApiClient,  RestApiVersion restApiVersion) {
        this.gson = gson;
        this.jiraApiClient = jiraApiClient;
        this.restApiVersion = restApiVersion;
    }

    public IssuePropertyKeysResponseModel getPropertyKeys(String issueKey) throws IntegrationException {
        String url = createApiUri(issueKey);
        HttpUrl httpUrl = new HttpUrl(url);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(httpUrl)
                                  .build();
        return jiraApiClient.get(request, IssuePropertyKeysResponseModel.class);
    }

    public IssuePropertyResponseModel getProperty(String issueKey, String propertyKey) throws IntegrationException {
        HttpUrl url = createApiUriWithKey(issueKey, propertyKey);
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(url)
                                  .build();
        return jiraApiClient.get(request, IssuePropertyResponseModel.class);
    }

    public void setProperty(String issueKey, String propertyKey, Serializable propertyValue) throws IntegrationException {
        String json = gson.toJson(propertyValue);
        setProperty(issueKey, propertyKey, json);
    }

    public void setProperty(String issueKey, String propertyKey, String jsonPropertyValue) throws IntegrationException {
        HttpUrl url = createApiUriWithKey(issueKey, propertyKey);
        jiraApiClient.put(jsonPropertyValue, url);
    }

    private HttpUrl createApiUriWithKey(String issueKey, String propertyKey) throws IntegrationException {
        return new HttpUrl(createApiUri(issueKey) + "/" + propertyKey);
    }

    private String createApiUri(String issueKey) {
        return jiraApiClient.getBaseUrl() + createUriPath() + "/" + issueKey + API_PATH_PROPERTIES_PIECE;
    }

    private String createUriPath() {
        return "/rest/api/" + restApiVersion.getVersion() + "/issue";
    }

}
