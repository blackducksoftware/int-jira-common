package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

public class IssueIncludedFieldsComponent {
    private List<String> included;
    private List<String> excluded;
    private List<String> actuallyIncluded;

    public IssueIncludedFieldsComponent() {
    }

    public IssueIncludedFieldsComponent(final List<String> included, final List<String> excluded, final List<String> actuallyIncluded) {
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
