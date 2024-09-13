/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.request.builder;

import java.util.Map;

public interface IssueRequestModelFieldsMapBuilder {
    Map<String, Object> build();

    IssueRequestModelFieldsMapBuilder copyFields(IssueRequestModelFieldsMapBuilder original);

}
