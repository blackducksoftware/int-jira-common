package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPagedResponseModel;

public class PageOfChangelogsComponent extends JiraPagedResponseModel {
    private List<ChangelogComponent> histories;

    public PageOfChangelogsComponent() {
    }

    public PageOfChangelogsComponent(final List<ChangelogComponent> histories) {
        this.histories = histories;
    }

    public List<ChangelogComponent> getHistories() {
        return histories;
    }

}
