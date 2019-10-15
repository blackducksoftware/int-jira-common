/**
 * int-jira-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.jira.common.model.request;

import java.util.List;

import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.VisibilityComponent;

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
