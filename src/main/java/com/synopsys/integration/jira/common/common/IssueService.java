package com.synopsys.integration.jira.common.common;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.model.response.IssueCommentResponseModel;

public interface IssueService {
    IssueCommentResponseModel addComment(IssueCommentRequestModel requestModel) throws IntegrationException;
}
