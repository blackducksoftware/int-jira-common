package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class CommentComponent extends JiraComponent {
    private String self;
    private String id;
    private UserDetailsComponent author;
    private String body;
    private UserDetailsComponent updateAuthor;
    private String created;
    private String updated;
    private Object visibility; // TODO

    public CommentComponent() {
    }

    public CommentComponent(final String self, final String id, final UserDetailsComponent author, final String body, final UserDetailsComponent updateAuthor, final String created, final String updated, final Object visibility) {
        this.self = self;
        this.id = id;
        this.author = author;
        this.body = body;
        this.updateAuthor = updateAuthor;
        this.created = created;
        this.updated = updated;
        this.visibility = visibility;
    }

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public UserDetailsComponent getUpdateAuthor() {
        return updateAuthor;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public Object getVisibility() {
        return visibility;
    }

}
