package com.synopsys.integration.jira.common.cloud.rest.service;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.cloud.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.rest.request.Request;

public class ProjectService {
    public static final String API_PATH = "/rest/api/2/project/search";
    private JiraCloudService jiraCloudService;

    public ProjectService(final JiraCloudService jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public PageOfProjectsResponseModel getProjects() throws IntegrationException {
        final Request request = JiraCloudRequestFactory.createDefaultGetRequest(createApiUri());
        return jiraCloudService.get(request, PageOfProjectsResponseModel.class);
    }

    private String createApiUri() {
        return jiraCloudService.getBaseUrl() + API_PATH;
    }

}
