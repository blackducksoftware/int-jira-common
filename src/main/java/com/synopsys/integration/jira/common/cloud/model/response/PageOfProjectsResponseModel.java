package com.synopsys.integration.jira.common.cloud.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.model.ProjectComponent;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class PageOfProjectsResponseModel extends JiraPageResponseModel {
    private List<ProjectComponent> projects;

    public PageOfProjectsResponseModel() {
    }

    public PageOfProjectsResponseModel(final List<ProjectComponent> projects) {
        this.projects = projects;
    }

    public List<ProjectComponent> getProjects() {
        return projects;
    }
}
