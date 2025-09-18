/*
 * int-jira-common
 *
 * Copyright (c) 2025 Black Duck Software, Inc. 
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import com.blackduck.integration.jira.common.model.components.IssueFieldsComponent;

public interface IssueResponseModel {
    String getId();
    String getKey();
    String getSelf();
    IssueFieldsComponent getFields();
}
