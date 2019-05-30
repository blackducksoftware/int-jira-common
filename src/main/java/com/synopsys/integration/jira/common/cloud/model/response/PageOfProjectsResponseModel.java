package com.synopsys.integration.jira.common.cloud.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.model.ProjectComponent;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class PageOfProjectsResponseModel extends JiraPageResponseModel {
    private List<ProjectComponent> values;

    public PageOfProjectsResponseModel() {
    }

    public PageOfProjectsResponseModel(final List<ProjectComponent> values) {
        this.values = values;
    }

    public List<ProjectComponent> getProjects() {
        return values;
    }
}
