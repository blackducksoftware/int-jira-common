/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.request.builder;

import java.util.Map;

public interface IssueRequestModelFieldsMapBuilder<T extends IssueRequestModelFieldsMapBuilder<T>> {
    Map<String, Object> build();

    T copyFields(IssueRequestModelFieldsMapBuilder original);

    T setSummary(String summary);
    T setDescription(String description);
    T setProject(String projectId);
    T setIssueType(String issueType);
    T setField(String key, Object value);
}
