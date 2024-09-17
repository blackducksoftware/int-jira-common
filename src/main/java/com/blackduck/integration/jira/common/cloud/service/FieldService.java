/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.service;

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

    private final JiraApiClient jiraCloudService;

    public FieldService(JiraApiClient jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public List<CustomFieldCreationResponseModel> getUserVisibleFields() throws IntegrationException {
        JiraRequest request = JiraRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraCloudService.getList(request, CustomFieldCreationResponseModel.class);
    }

    public CustomFieldCreationResponseModel createCustomField(FieldRequestModel fieldModel) throws IntegrationException {
        return jiraCloudService.post(fieldModel, createApiUri(), CustomFieldCreationResponseModel.class);
    }

    private HttpUrl createApiUri() throws IntegrationException {
        return new HttpUrl(jiraCloudService.getBaseUrl() + API_PATH);
    }

}
