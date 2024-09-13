/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.request;

import java.util.List;

import com.blackduck.integration.jira.common.model.EntityProperty;
import com.blackduck.integration.jira.common.model.components.VisibilityComponent;

public class IssueCommentRequestModel extends JiraRequestModel {
    private final String issueIdOrKey;
    private final String body;
    private final VisibilityComponent visibility;
    private final Boolean jsdPublic;
    private final List<EntityProperty> properties;

    public IssueCommentRequestModel(final String issueIdOrKey, final String body) {
        this(issueIdOrKey, body, null, null, null);
    }

    public IssueCommentRequestModel(final String issueIdOrKey, final String body, final VisibilityComponent visibility, final Boolean jsdPublic, final List<EntityProperty> properties) {
        this.issueIdOrKey = issueIdOrKey;
        this.body = body;
        this.visibility = visibility;
        this.jsdPublic = jsdPublic;
        this.properties = properties;
    }

    public String getBody() {
        return body;
    }

    public String getIssueIdOrKey() {
        return issueIdOrKey;
    }

    public VisibilityComponent getVisibility() {
        return visibility;
    }

    public Boolean getJsdPublic() {
        return jsdPublic;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }
}
