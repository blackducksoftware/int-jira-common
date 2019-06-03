package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;

public class PageOfWorklogsComponent extends JiraPageResponseModel {
    private List<WorklogComponent> worklogs;

    public PageOfWorklogsComponent() {
    }

    public PageOfWorklogsComponent(final List<WorklogComponent> worklogs) {
        this.worklogs = worklogs;
    }

    public List<WorklogComponent> getWorklogs() {
        return worklogs;
    }
}
