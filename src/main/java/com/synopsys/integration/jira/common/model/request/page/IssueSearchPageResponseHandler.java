/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
