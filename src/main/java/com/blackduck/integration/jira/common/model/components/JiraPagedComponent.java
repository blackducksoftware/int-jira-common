/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.jira.common.model.JiraPagedModel;
import com.blackduck.integration.rest.component.IntRestComponent;

public class JiraPagedComponent extends IntRestComponent implements JiraPagedModel {
    private Integer startAt;
    private Integer maxResults;
    private Integer total;

    public JiraPagedComponent() {
    }

    public JiraPagedComponent(Integer startAt, Integer maxResults, Integer total) {
        this.startAt = startAt;
        this.maxResults = maxResults;
        this.total = total;
    }

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
