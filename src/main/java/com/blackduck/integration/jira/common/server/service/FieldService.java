/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.service;

import java.util.List;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.FieldRequestModel;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.response.CustomFieldCreationResponseModel;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.rest.HttpUrl;

public class FieldService {
    public static final String API_PATH = "/rest/api/2/field";

    private final JiraApiClient jiraApiClient;

    public FieldService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public List<CustomFieldCreationResponseModel> getUserVisibleFields() throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraApiClient.getList(request, CustomFieldCreationResponseModel.class);
    }

    public CustomFieldCreationResponseModel createCustomField(FieldRequestModel fieldModel) throws IntegrationException {
        return jiraApiClient.post(fieldModel, createApiUri(), CustomFieldCreationResponseModel.class);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH);
    }

}
