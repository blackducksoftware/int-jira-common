/**
 * int-jira-common
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.jira.common.enumeration;

public enum FieldType {
    CASCADING_SELECT("com.atlassian.jira.plugin.system.customfieldtypes:cascadingselect", "com.atlassian.jira.plugin.system.customfieldtypes:cascadingselectsearcher"),
    DATE_PICKER("com.atlassian.jira.plugin.system.customfieldtypes:datepicker", "com.atlassian.jira.plugin.system.customfieldtypes:daterange"),
    DATE_TIME("com.atlassian.jira.plugin.system.customfieldtypes:datetime", "com.atlassian.jira.plugin.system.customfieldtypes:datetimerange"),
    FLOAT("com.atlassian.jira.plugin.system.customfieldtypes:float", "com.atlassian.jira.plugin.system.customfieldtypes:exactnumber"),
    GROUP_PICKER("com.atlassian.jira.plugin.system.customfieldtypes:grouppicker", "com.atlassian.jira.plugin.system.customfieldtypes:grouppickersearcher"),
    IMPORT_ID("com.atlassian.jira.plugin.system.customfieldtypes:importid", "com.atlassian.jira.plugin.system.customfieldtypes:exactnumber"),
    LABELS("com.atlassian.jira.plugin.system.customfieldtypes:labels", "com.atlassian.jira.plugin.system.customfieldtypes:labelsearcher"),
    MULTI_CHECKBOXES("com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes", "com.atlassian.jira.plugin.system.customfieldtypes:multiselectsearcher"),
    MULTI_GROUP_PICKER("com.atlassian.jira.plugin.system.customfieldtypes:multigrouppicker", "com.atlassian.jira.plugin.system.customfieldtypes:multiselectsearcher"),
    MULTI_SELECT("com.atlassian.jira.plugin.system.customfieldtypes:multiselect", "com.atlassian.jira.plugin.system.customfieldtypes:multiselectsearcher"),
    MULTI_USER_PICKER("com.atlassian.jira.plugin.system.customfieldtypes:multiuserpicker", "com.atlassian.jira.plugin.system.customfieldtypes:userpickergroupsearcher"),
    MULTI_VERSION("com.atlassian.jira.plugin.system.customfieldtypes:multiversion", "com.atlassian.jira.plugin.system.customfieldtypes:versionsearcher"),
    PROJECT("com.atlassian.jira.plugin.system.customfieldtypes:project", "com.atlassian.jira.plugin.system.customfieldtypes:projectsearcher"),
    RADIO_BUTTONS("com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons", "com.atlassian.jira.plugin.system.customfieldtypes:multiselectsearcher"),
    READ_ONLY_FIELD("com.atlassian.jira.plugin.system.customfieldtypes:readonlyfield", "com.atlassian.jira.plugin.system.customfieldtypes:textsearcher"),
    SELECT("com.atlassian.jira.plugin.system.customfieldtypes:select", "com.atlassian.jira.plugin.system.customfieldtypes:multiselectsearcher"),
    TEXT_AREA("com.atlassian.jira.plugin.system.customfieldtypes:textarea", "com.atlassian.jira.plugin.system.customfieldtypes:textsearcher"),
    TEXT_FIELD("com.atlassian.jira.plugin.system.customfieldtypes:textfield", "com.atlassian.jira.plugin.system.customfieldtypes:textsearcher"),
    URL("com.atlassian.jira.plugin.system.customfieldtypes:url", "com.atlassian.jira.plugin.system.customfieldtypes:exacttextsearcher"),
    USER_PICKER("com.atlassian.jira.plugin.system.customfieldtypes:userpicker", "om.atlassian.jira.plugin.system.customfieldtypes:userpickergroupsearcher"),
    VERSION("com.atlassian.jira.plugin.system.customfieldtypes:version", "com.atlassian.jira.plugin.system.customfieldtypes:versionsearcher");

    private String typeKey;
    private String searcherKey;

    private FieldType(final String typeKey, final String searcherKey) {
        this.typeKey = typeKey;
        this.searcherKey = searcherKey;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public String getSearcherKey() {
        return searcherKey;
    }

}
