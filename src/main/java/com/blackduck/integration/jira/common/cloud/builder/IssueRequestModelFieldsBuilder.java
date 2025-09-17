/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blackduck.integration.jira.common.model.request.builder.IssueRequestModelFieldsMapBuilder;

public class IssueRequestModelFieldsBuilder implements IssueRequestModelFieldsMapBuilder {
    public static final String SUMMARY = "summary";
    public static final String ISSUE_TYPE = "issuetype";
    public static final String COMPONENTS = "components";
    public static final String PROJECT = "project";
    public static final String DESCRIPTION = "description";
    public static final String REPORTER = "reporter";
    public static final String FIX_VERSIONS = "fixVersions";
    public static final String PRIORITY = "priority";
    public static final String LABELS = "labels";
    public static final String TIME_TRACKING = "timetracking";
    public static final String SECURITY = "security";
    public static final String ENVIRONMENT = "environment";
    public static final String VERSIONS = "versions";
    public static final String DUE_DATE = "duedate";
    public static final String ASSIGNEE = "assignee";

    private final Map<String, Object> issueFields;

    public IssueRequestModelFieldsBuilder() {
        this.issueFields = new HashMap<>();
    }

    @Override
    public IssueRequestModelFieldsBuilder copyFields(IssueRequestModelFieldsMapBuilder original) {
        Map<String, Object> originalFields = original.build();
        issueFields.putAll(originalFields);
        return this;
    }

    @Override
    public Map<String, Object> build() {
        return Collections.unmodifiableMap(issueFields);
    }

    public IssueRequestModelFieldsBuilder setValue(String key, Object value) {
        issueFields.put(key, value);
        return this;
    }

    public IssueRequestModelFieldsBuilder setSummary(String summary) {
        return setValue(SUMMARY, summary);
    }

    public IssueRequestModelFieldsBuilder setIssueType(String issueTypeId) {
        return setIdField(ISSUE_TYPE, issueTypeId);
    }

    public IssueRequestModelFieldsBuilder setComponents(Collection<String> componentIds) {
        return setIdFields(COMPONENTS, componentIds);
    }

    public IssueRequestModelFieldsBuilder setProject(String projectId) {
        return setIdField(PROJECT, projectId);
    }

    public IssueRequestModelFieldsBuilder setDescription(String description) {
        DescriptionDocumentObject descriptionObject = new DescriptionDocumentObject(description);
        return setValue(DESCRIPTION, descriptionObject);
    }

    public IssueRequestModelFieldsBuilder setReporterId(String reporterId) {
        return setIdField(REPORTER, reporterId);
    }

    public IssueRequestModelFieldsBuilder setFixVersions(Collection<String> versionIds) {
        return setIdFields(FIX_VERSIONS, versionIds);
    }

    public IssueRequestModelFieldsBuilder setPriority(String priorityId) {
        return setIdField(PRIORITY, priorityId);
    }

    public IssueRequestModelFieldsBuilder setLabels(Collection<String> labels) {
        return setValue(LABELS, labels);
    }

    public IssueRequestModelFieldsBuilder setTimeTracking(String remainingEstimate, String originalEstimate) {
        Map<String, String> timeTrackingMap = new HashMap<>();
        timeTrackingMap.put("remainingEstimate", remainingEstimate);
        timeTrackingMap.put("originalEstimate", originalEstimate);
        return setValue(TIME_TRACKING, timeTrackingMap);
    }

    public IssueRequestModelFieldsBuilder setSecurity(String securityId) {
        return setIdField(SECURITY, securityId);
    }

    public IssueRequestModelFieldsBuilder setEnvironment(String environment) {
        return setValue(ENVIRONMENT, environment);
    }

    public IssueRequestModelFieldsBuilder setVersions(Collection<String> versionIds) {
        return setIdFields(VERSIONS, versionIds);
    }

    public IssueRequestModelFieldsBuilder setDueDate(String dueDate) {
        return setValue(DUE_DATE, dueDate);
    }

    public IssueRequestModelFieldsBuilder setAssigneeId(String assigneeId) {
        return setIdField(ASSIGNEE, assigneeId);
    }

    private IssueRequestModelFieldsBuilder setIdField(String key, String value) {
        ObjectWithId issueTypeObject = new ObjectWithId(value);
        return setValue(key, issueTypeObject);
    }

    private IssueRequestModelFieldsBuilder setIdFields(String key, Collection<String> values) {
        List<ObjectWithId> newObjects = new ArrayList<>();
        values
            .stream()
            .map(ObjectWithId::new)
            .forEach(newObjects::add);
        return setValue(key, newObjects);
    }

    private class ObjectWithId {
        private final String id;

        public ObjectWithId(String id) {
            this.id = id;
        }

    }

    private class DescriptionDocumentObject {
        private final String type;
        private final int version;
        private List<AtlassianDocumentContent> content;

        public DescriptionDocumentObject(String description) {
            this.type = "doc";
            this.version = 1;
            this.content = new ArrayList<>();
            Map<String,Object> descriptionContent = new HashMap<>();
            descriptionContent.put("type", "text");
            descriptionContent.put("text", description);
            List<Map<String, Object>> descriptionContentList = new ArrayList<>();
            descriptionContentList.add(descriptionContent);
            content.add(new AtlassianDocumentContent("paragraph", descriptionContentList));
        }
    }

    private class AtlassianDocumentContent {
        private String type;
        private List<Map<String, Object>> content;

        public AtlassianDocumentContent(String type, List<Map<String, Object>> content) {
            this.type = type;
            this.content = content;
        }
    }

}
