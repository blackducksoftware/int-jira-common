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
package com.synopsys.integration.jira.common.model.request.page;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.synopsys.integration.jira.common.cloud.model.IssueSearchResponseModel;
import com.synopsys.integration.jira.common.rest.JiraCloudPageRequestHandler;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.component.IntRestResponse;

public class IssueSearchPageResponseHandler extends JiraCloudPageRequestHandler {
    public IssueSearchPageResponseHandler(IntLogger logger) {
        super(logger);
    }

    @Override
    public <R extends IntRestResponse> R combineResponses(Collection<R> pagedResponses) {
        if (null == pagedResponses || pagedResponses.isEmpty()) {
            return (R) createEmpty();
        }

        IssueSearchResponseModel unifiedIssueSearchResponse = null;
        for (R response : pagedResponses) {
            IssueSearchResponseModel issueSearchResponse = (IssueSearchResponseModel) response;
            if (null == unifiedIssueSearchResponse) {
                unifiedIssueSearchResponse = new IssueSearchResponseModel();
                unifiedIssueSearchResponse.setExpand(issueSearchResponse.getExpand());
                unifiedIssueSearchResponse.setNames(issueSearchResponse.getNames());
                unifiedIssueSearchResponse.setSchema(issueSearchResponse.getSchema());
                unifiedIssueSearchResponse.setIssues(new LinkedList<>());
                unifiedIssueSearchResponse.setWarningMessages(new LinkedList<>());
            }
            unifiedIssueSearchResponse.getIssues().addAll(issueSearchResponse.getIssues());
            unifiedIssueSearchResponse.getWarningMessages().addAll(issueSearchResponse.getWarningMessages());
        }
        return (R) unifiedIssueSearchResponse;
    }

    private IssueSearchResponseModel createEmpty() {
        IssueSearchResponseModel issueSearchResponse = new IssueSearchResponseModel();
        issueSearchResponse.setIssues(Collections.emptyList());
        issueSearchResponse.setWarningMessages(Collections.emptyList());
        return issueSearchResponse;
    }

}
