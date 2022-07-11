/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import java.util.List;

import com.synopsys.integration.rest.component.IntRestComponent;

public class ChangelogComponent extends IntRestComponent {
    private String id;
    private UserDetailsComponent author;
    private String created;
    private List<ChangeDetailsComponent> items;
    private HistoryMetadataComponent historyMetadata;

    public ChangelogComponent() {
    }

    public ChangelogComponent(final String id, final UserDetailsComponent author, final String created, final List<ChangeDetailsComponent> items, final HistoryMetadataComponent historyMetadata) {
        this.id = id;
        this.author = author;
        this.created = created;
        this.items = items;
        this.historyMetadata = historyMetadata;
    }

    public String getId() {
        return id;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public List<ChangeDetailsComponent> getItems() {
        return items;
    }

    public HistoryMetadataComponent getHistoryMetadata() {
        return historyMetadata;
    }

}
