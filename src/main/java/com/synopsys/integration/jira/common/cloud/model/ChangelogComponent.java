package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class ChangelogComponent extends JiraComponent {
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
