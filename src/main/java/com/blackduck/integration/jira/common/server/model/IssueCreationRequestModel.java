/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.model;

import com.blackduck.integration.jira.common.model.request.builder.IssueRequestModelFieldsMapBuilder;

public class IssueCreationRequestModel {
    private final String reporterUsername;
    private final String issueTypeName;
    private final String projectName;
    private final IssueRequestModelFieldsMapBuilder fieldsBuilder;

    public IssueCreationRequestModel(String reporterUsername, String issueTypeName, String projectName, IssueRequestModelFieldsMapBuilder fieldsBuilder) {
        this.reporterUsername = reporterUsername;
        this.issueTypeName = issueTypeName;
        this.projectName = projectName;
        this.fieldsBuilder = fieldsBuilder;
    }

    public String getReporterUsername() {
        return reporterUsername;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public IssueRequestModelFieldsMapBuilder getFieldsBuilder() {
        return fieldsBuilder;
    }

}
