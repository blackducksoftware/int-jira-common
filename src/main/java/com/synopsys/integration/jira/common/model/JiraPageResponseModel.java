/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model;

public class JiraPageResponseModel extends JiraResponseModel implements JiraPagedModel {
    private Integer startAt;
    private Integer maxResults;
    private Integer total;

    @Override
    public Integer getStartAt() {
        return startAt;
    }

    @Override
    public Integer getMaxResults() {
        return maxResults;
    }

    @Override
    public Integer getTotal() {
        return total;
    }

}
