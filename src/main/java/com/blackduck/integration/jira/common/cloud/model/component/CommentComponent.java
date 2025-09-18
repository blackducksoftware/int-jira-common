/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.model.component;

import com.blackduck.integration.jira.common.cloud.model.AtlassianDocumentFormatModel;
import com.blackduck.integration.jira.common.model.components.UserDetailsComponent;
import com.blackduck.integration.jira.common.model.components.VisibilityComponent;
import com.blackduck.integration.rest.component.IntRestComponent;

public class CommentComponent extends IntRestComponent {
    private String self;
    private String id;
    private UserDetailsComponent author;
    private AtlassianDocumentFormatModel body;
    private UserDetailsComponent updateAuthor;
    private String created;
    private String updated;
    private VisibilityComponent visibility;

    public CommentComponent() {
    }

    public CommentComponent(String self, String id, UserDetailsComponent author, AtlassianDocumentFormatModel body, UserDetailsComponent updateAuthor, String created, String updated,
        VisibilityComponent visibility) {
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

    public AtlassianDocumentFormatModel getBody() {
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
