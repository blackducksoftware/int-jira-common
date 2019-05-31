package com.synopsys.integration.jira.common.cloud.rest.service;

import java.util.List;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.request.IssueTypeRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.cloud.model.response.IssueTypeResponseModel;
import com.synopsys.integration.rest.request.Request;

public class IssueTypeService {
    private static final String API_PATH = "/rest/api/2/issuetype";

    private JiraCloudService jiraCloudService;

    public IssueTypeService(final JiraCloudService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public List<IssueTypeResponseModel> getAllIssueTypes() throws IntegrationException {
        final String uri = createApiUri();
        Request request = JiraCloudRequestFactory.createDefaultGetRequest(uri);
        return jiraCloudService.getList(request, IssueTypeResponseModel.class);
    }

    public IssueTypeResponseModel createIssueType(IssueTypeRequestModel issueTypeRequestModel) throws IntegrationException {
        final String uri = createApiUri();
        return jiraCloudService.post(issueTypeRequestModel, uri, IssueTypeResponseModel.class);
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }
}
