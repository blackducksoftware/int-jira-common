/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.Collections;
import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;

public class PageOfProjectsResponseModel extends JiraPageResponseModel {
    private List<ProjectComponent> values;

    public PageOfProjectsResponseModel() {
        values = Collections.emptyList();
    }

    public PageOfProjectsResponseModel(final List<ProjectComponent> values) {
        this.values = values;
    }

    public List<ProjectComponent> getProjects() {
        return values;
    }
}
