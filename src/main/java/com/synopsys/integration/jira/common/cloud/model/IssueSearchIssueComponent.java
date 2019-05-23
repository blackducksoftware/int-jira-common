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
