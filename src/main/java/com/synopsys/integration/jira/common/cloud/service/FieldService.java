/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.service;

import java.util.List;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.FieldRequestModel;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.CustomFieldCreationResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

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
