package com.synopsys.integration.jira.common.model;

// TODO a JsonTransformer (or custom TypeAdapter) will be required for these fields to be deserialized
public class JiraPagedResponseModel extends JiraResponse {
    private Integer startAt;
    private Integer maxResults;
    private Integer total;

    public Integer getStartAt() {
        return startAt;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public Integer getTotal() {
        return total;
    }

}
