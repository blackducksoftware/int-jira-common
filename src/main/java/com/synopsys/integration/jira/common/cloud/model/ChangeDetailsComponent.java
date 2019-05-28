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

import com.synopsys.integration.rest.component.IntRestComponent;

public class ChangeDetailsComponent extends IntRestComponent {
    private String field;
    private String fieldtype;
    private String fieldId;
    private String from;
    private String fromString;
    private String to;
    private String toString;

    public ChangeDetailsComponent() {
    }

    public ChangeDetailsComponent(final String field, final String fieldtype, final String fieldId, final String from, final String fromString, final String to, final String toString) {
        this.field = field;
        this.fieldtype = fieldtype;
        this.fieldId = fieldId;
        this.from = from;
        this.fromString = fromString;
        this.to = to;
        this.toString = toString;
    }

    public String getField() {
        return field;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public String getFieldId() {
        return fieldId;
    }

    public String getFrom() {
        return from;
    }

    public String getFromString() {
        return fromString;
    }

    public String getTo() {
        return to;
    }

    public String getToString() {
        return toString;
    }

}
