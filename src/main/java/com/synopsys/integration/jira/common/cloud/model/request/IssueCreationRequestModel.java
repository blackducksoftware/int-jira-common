package com.synopsys.integration.jira.common.cloud.model.request;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.model.EntityProperty;

public class IssueCreationRequestModel {
    private final String reporterEmail;
    private final String issueTypeName;
    private final String projectName;
    private final IssueRequestModelFieldsBuilder fieldsBuilder;
    private final List<EntityProperty> properties;

    public IssueCreationRequestModel(final String reporterEmail, final String issueTypeName, final String projectName, final IssueRequestModelFieldsBuilder fieldsBuilder, final List<EntityProperty> properties) {
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

    public IssueRequestModelFieldsBuilder getFieldsBuilder() {
        return fieldsBuilder;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }
}
