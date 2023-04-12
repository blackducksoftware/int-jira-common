/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class CommentComponent extends IntRestComponent {
    private String self;
    private String id;
    private UserDetailsComponent author;
    private String body;
    private UserDetailsComponent updateAuthor;
    private String created;
    private String updated;
    private VisibilityComponent visibility;

    public CommentComponent() {
    }

    public CommentComponent(final String self, final String id, final UserDetailsComponent author, final String body, final UserDetailsComponent updateAuthor, final String created, final String updated,
        final VisibilityComponent visibility) {
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
