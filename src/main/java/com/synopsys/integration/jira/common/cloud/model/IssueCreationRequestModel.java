/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;

import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.request.builder.IssueRequestModelFieldsMapBuilder;

public class IssueCreationRequestModel {
    private final String reporterEmail;
    private final String issueTypeName;
    private final String projectName;
    private final IssueRequestModelFieldsMapBuilder fieldsBuilder;
    private final List<EntityProperty> properties;

    public IssueCreationRequestModel(String reporterEmail, String issueTypeName, String projectName, IssueRequestModelFieldsMapBuilder fieldsBuilder, List<EntityProperty> properties) {
        this.reporterEmail = reporterEmail;
        this.issueTypeName = issueTypeName;
        this.projectName = projectName;
        this.fieldsBuilder = fieldsBuilder;
        this.properties = properties;
    }

    public String getReporterEmail() {
        return reporterEmail;
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

    public List<EntityProperty> getProperties() {
        return properties;
    }

}
