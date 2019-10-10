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
package com.synopsys.integration.jira.common.model.components;

import com.synopsys.integration.rest.component.IntRestComponent;

public class IssueLinksComponent extends IntRestComponent {
    private String id;
    private Object type; // TODO
    private Object outwardIssue; // TODO

    public IssueLinksComponent() {
    }

    public IssueLinksComponent(final String id, final Object type, final Object outwardIssue) {
        this.id = id;
        this.type = type;
        this.outwardIssue = outwardIssue;
    }

    public String getId() {
        return id;
    }

    public Object getType() {
        return type;
    }

    public Object getOutwardIssue() {
        return outwardIssue;
    }

}
