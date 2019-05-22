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
package com.synopsys.integration.jira.common.cloud.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueRequestModelFieldsBuilder {
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

    public Map<String, Object> build() {
        return Collections.unmodifiableMap(issueFields);
    }

    public IssueRequestModelFieldsBuilder setSummary(final String summary) {
        issueFields.put(SUMMARY, summary);
        return this;
    }

    public IssueRequestModelFieldsBuilder setIssueType(final String issueTypeId) {
        return setField(ISSUE_TYPE, issueTypeId);
    }

    public IssueRequestModelFieldsBuilder setComponents(final Collection<String> componentIds) {
        return setFields(COMPONENTS, componentIds);
    }

    public IssueRequestModelFieldsBuilder setProject(final String projectId) {
        return setField(PROJECT, projectId);
    }

    public IssueRequestModelFieldsBuilder setDescription(final String description) {
        issueFields.put(DESCRIPTION, description);
        return this;
    }

    public IssueRequestModelFieldsBuilder setReporter(final String reporterId) {
        return setField(REPORTER, reporterId);
    }

    public IssueRequestModelFieldsBuilder setFixVersions(final Collection<String> versionIds) {
        return setFields(FIX_VERSIONS, versionIds);
    }

    public IssueRequestModelFieldsBuilder setPriority(final String priorityId) {
        return setField(PRIORITY, priorityId);
    }

    public IssueRequestModelFieldsBuilder setLabels(final Collection<String> labels) {
        issueFields.put(LABELS, labels);
        return this;
    }

    public IssueRequestModelFieldsBuilder setTimeTracking(final String remainingEstimate, final String originalEstimate) {
        final Map<String, String> timeTrackingMap = new HashMap<>();
        timeTrackingMap.put("remainingEstimate", remainingEstimate);
        timeTrackingMap.put("originalEstimate", originalEstimate);
        issueFields.put(TIME_TRACKING, timeTrackingMap);
        return this;
    }

    public IssueRequestModelFieldsBuilder setSecurity(final String securityId) {
        return setField(SECURITY, securityId);
    }

    public IssueRequestModelFieldsBuilder setEnvironment(final String environment) {
        issueFields.put(ENVIRONMENT, environment);
        return this;
    }

    public IssueRequestModelFieldsBuilder setVersions(final Collection<String> versionIds) {
        return setFields(VERSIONS, versionIds);
    }

    public IssueRequestModelFieldsBuilder setDueDate(final String dueDate) {
        issueFields.put(DUE_DATE, dueDate);
        return this;
    }

    public IssueRequestModelFieldsBuilder setAssignee(final String assigneeId) {
        return setField(ASSIGNEE, assigneeId);
    }

    private IssueRequestModelFieldsBuilder setField(final String key, final String value) {
        final ObjectWithId issueTypeObject = new ObjectWithId(value);
        issueFields.put(key, issueTypeObject);
        return this;
    }

    private IssueRequestModelFieldsBuilder setFields(final String key, final Collection<String> values) {
        final List<ObjectWithId> newObjects = new ArrayList<>();
        values
            .stream()
            .map(ObjectWithId::new)
            .forEach(newObjects::add);
        issueFields.put(key, newObjects);
        return this;
    }

    private class ObjectWithId {
        private final String id;

        public ObjectWithId(final String id) {
            this.id = id;
        }

    }

}
