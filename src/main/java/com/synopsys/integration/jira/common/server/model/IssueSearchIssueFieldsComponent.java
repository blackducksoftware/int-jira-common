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
package com.synopsys.integration.jira.common.server.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.components.IssuePriorityComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.model.components.UserDetailsComponent;
import com.synopsys.integration.jira.common.model.components.WatcherComponent;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.rest.component.IntRestComponent;

public class IssueSearchIssueFieldsComponent extends IntRestComponent {
    @SerializedName("issuetype")
    private IssueTypeResponseModel issueType;
    private List<Object> components; // TODO
    private String description;
    private ProjectComponent project;
    private List<Object> fixVersions; // TODO
    @SerializedName("workratio")
    private Integer workRatio;
    private String summary;
    @SerializedName("watches")
    private WatcherComponent watchers;
    private UserDetailsComponent creator;
    @SerializedName("subtasks")
    private List<Object> subTasks; // TODO
    private String created;
    private UserDetailsComponent reporter;
    @SerializedName("aggregateprogress")
    private Object aggregateProgress; // TODO
    private IssuePriorityComponent priority;
    private List<String> labels;
    private List<Object> versions; // TODO
    private Object progress; // TODO
    @SerializedName("issuelinks")
    private List<Object> issueLinks;
    private Object votes; // TODO
    private String updated;
    private StatusDetailsComponent status;

    public IssueSearchIssueFieldsComponent() {
    }

    public IssueSearchIssueFieldsComponent(
        IssueTypeResponseModel issueType,
        List<Object> components,
        String description,
        ProjectComponent project,
        List<Object> fixVersions,
        Integer workRatio,
        String summary,
        WatcherComponent watchers,
        UserDetailsComponent creator,
        List<Object> subTasks,
        String created,
        UserDetailsComponent reporter,
        Object aggregateProgress,
        IssuePriorityComponent priority,
        List<String> labels,
        List<Object> versions,
        Object progress,
        List<Object> issueLinks,
        Object votes,
        String updated,
        StatusDetailsComponent status
    ) {
        this.issueType = issueType;
        this.components = components;
        this.description = description;
        this.project = project;
        this.fixVersions = fixVersions;
        this.workRatio = workRatio;
        this.summary = summary;
        this.watchers = watchers;
        this.creator = creator;
        this.subTasks = subTasks;
        this.created = created;
        this.reporter = reporter;
        this.aggregateProgress = aggregateProgress;
        this.priority = priority;
        this.labels = labels;
        this.versions = versions;
        this.progress = progress;
        this.issueLinks = issueLinks;
        this.votes = votes;
        this.updated = updated;
        this.status = status;
    }

    public IssueTypeResponseModel getIssueType() {
        return issueType;
    }

    public List<Object> getComponents() {
        return components;
    }

    public String getDescription() {
        return description;
    }

    public ProjectComponent getProject() {
        return project;
    }

    public List<Object> getFixVersions() {
        return fixVersions;
    }

    public Integer getWorkRatio() {
        return workRatio;
    }

    public String getSummary() {
        return summary;
    }

    public WatcherComponent getWatchers() {
        return watchers;
    }

    public UserDetailsComponent getCreator() {
        return creator;
    }

    public List<Object> getSubTasks() {
        return subTasks;
    }

    public String getCreated() {
        return created;
    }

    public UserDetailsComponent getReporter() {
        return reporter;
    }

    public Object getAggregateProgress() {
        return aggregateProgress;
    }

    public Object getPriority() {
        return priority;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Object> getVersions() {
        return versions;
    }

    public Object getProgress() {
        return progress;
    }

    public List<Object> getIssueLinks() {
        return issueLinks;
    }

    public Object getVotes() {
        return votes;
    }

    public String getUpdated() {
        return updated;
    }

    public StatusDetailsComponent getStatus() {
        return status;
    }

}
