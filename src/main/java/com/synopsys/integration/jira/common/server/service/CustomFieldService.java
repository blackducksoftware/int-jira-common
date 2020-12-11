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
package com.synopsys.integration.jira.common.server.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.jira.common.server.model.CustomFieldPageResponseModel;
import com.synopsys.integration.rest.HttpUrl;

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
