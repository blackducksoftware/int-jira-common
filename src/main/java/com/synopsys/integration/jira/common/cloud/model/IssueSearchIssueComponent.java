package com.synopsys.integration.jira.common.cloud.model;

import java.util.List;
import java.util.Map;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueSearchIssueComponent extends JiraComponent {
    private String expand;
    private String id;
    private String self;
    private String key;
    private Map<String, Object> renderedFields;
    private Map<String, Object> properties;
    private Map<String, Object> names;
    private Map<String, Object> schema; // TODO maybe a Map<String, SchemaComponent> ?
    private List<IdComponent> transitions;
    private OperationsComponent operations;
    private IssueUpdateMetadataComponent editmeta;
    private PageOfChangelogsComponent changelog;
    private Object versionedRepresentations; // TODO
    private IssueSeachIncludedFieldsComponent fieldsToInclude;
    private IssueSearchIssueFieldsComponent fields;

    public IssueSearchIssueComponent() {
    }

}
