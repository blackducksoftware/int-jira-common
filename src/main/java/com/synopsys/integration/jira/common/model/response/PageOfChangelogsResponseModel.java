/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.Collections;
import java.util.List;

import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.jira.common.model.components.ChangelogComponent;

public class PageOfChangelogsResponseModel extends JiraPageResponseModel {
    private List<ChangelogComponent> histories;

    public PageOfChangelogsResponseModel() {
        histories = Collections.emptyList();
    }

    public PageOfChangelogsResponseModel(final List<ChangelogComponent> histories) {
        this.histories = histories;
    }

    public List<ChangelogComponent> getHistories() {
        return histories;
    }

}
