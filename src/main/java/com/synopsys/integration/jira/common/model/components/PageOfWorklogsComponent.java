/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import java.util.Collections;
import java.util.List;

public class PageOfWorklogsComponent extends JiraPagedComponent {
    private List<WorklogComponent> worklogs;

    public PageOfWorklogsComponent() {
        worklogs = Collections.emptyList();
    }

    public PageOfWorklogsComponent(final List<WorklogComponent> worklogs) {
        this.worklogs = worklogs;
    }

    public List<WorklogComponent> getWorklogs() {
        return worklogs;
    }
}
