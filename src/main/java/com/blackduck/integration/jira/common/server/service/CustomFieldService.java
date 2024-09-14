/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.service;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.jira.common.server.model.CustomFieldPageResponseModel;
import com.blackduck.integration.rest.HttpUrl;

// https://docs.atlassian.com/software/jira/docs/api/REST/8.4.0/#api/2/customFields-getCustomFields
public class CustomFieldService {
    public static final String API_PATH = "/rest/api/2/customFields";

    private final JiraApiClient jiraApiClient;

    public CustomFieldService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public CustomFieldPageResponseModel getCustomFields() throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraApiClient.get(request, CustomFieldPageResponseModel.class);
    }

    public CustomFieldPageResponseModel getCustomFields(int startAt, int maxResults, String search) throws IntegrationException {
        JiraRequest.Builder requestBuilder = JiraRequestFactory.createDefaultBuilder()
                                                 .url(createApiUri())
                                                 .addQueryParamIfValueNotBlank("search", search);
        JiraRequest request = JiraRequestFactory.populatePageRequestBuilder(requestBuilder, maxResults, startAt)
                                  .build();
        return jiraApiClient.get(request, CustomFieldPageResponseModel.class);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);
    }

}
