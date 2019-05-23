package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueSeachIncludedFieldsComponent extends JiraComponent {
    private List<String> included;
    private List<String> excluded;
    private List<String> actuallyIncluded;

    public IssueSeachIncludedFieldsComponent() {
    }

    public IssueSeachIncludedFieldsComponent(final List<String> included, final List<String> excluded, final List<String> actuallyIncluded) {
        this.included = included;
        this.excluded = excluded;
        this.actuallyIncluded = actuallyIncluded;
    }

    public List<String> getIncluded() {
        return included;
    }

    public List<String> getExcluded() {
        return excluded;
    }

    public List<String> getActuallyIncluded() {
        return actuallyIncluded;
    }

}
