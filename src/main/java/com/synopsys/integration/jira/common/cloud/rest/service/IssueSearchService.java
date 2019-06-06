/**
 * int-jira-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.jira.common.cloud.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.enumeration.ExpandableTypes;
import com.synopsys.integration.jira.common.cloud.enumeration.QueryValidationStrategy;
import com.synopsys.integration.jira.common.cloud.model.request.IssueSearchRequestModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueSearchResponseModel;

public class IssueSearchService {
    public static final String API_PATH = "/rest/api/2/search";
    private JiraCloudService jiraCloudService;

    public IssueSearchService(final JiraCloudService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public IssueSearchResponseModel findIssuesByComment(final String comment) throws IntegrationException {
        final String jql = String.format("comment ~ '%s'", comment);
        final List<ExpandableTypes> typesToExpand = new ArrayList<>();
        typesToExpand.addAll(Arrays.asList(ExpandableTypes.values()));
        List<String> properties = Collections.emptyList();

        final IssueSearchRequestModel requestModel = new IssueSearchRequestModel(jql, null, null,
            IssueSearchRequestModel.ALL_FIELDS_LIST, QueryValidationStrategy.STRICT, typesToExpand, properties, false);
        return findIssue(requestModel);
    }

    private IssueSearchResponseModel findIssue(IssueSearchRequestModel requestModel) throws IntegrationException {
        return jiraCloudService.post(requestModel, createApiUri(), IssueSearchResponseModel.class);
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

}
