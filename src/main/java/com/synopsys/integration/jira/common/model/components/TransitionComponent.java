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
package com.synopsys.integration.jira.common.model.components;

import java.util.Map;

import com.synopsys.integration.rest.component.IntRestComponent;

public class TransitionComponent extends IntRestComponent {
    private String id;
    private String name;
    private StatusDetailsComponent to;
    private Boolean hasScreen;
    private Boolean isGlobal;
    private Boolean isInitial;
    private Map<String, Object> fields;
    private String expand;

    public TransitionComponent() {
    }

    public TransitionComponent(final String id, final String name, final StatusDetailsComponent to, final Boolean hasScreen, final Boolean isGlobal, final Boolean isInitial, final Map<String, Object> fields, final String expand) {
        this.id = id;
        this.name = name;
        this.to = to;
        this.hasScreen = hasScreen;
        this.isGlobal = isGlobal;
        this.isInitial = isInitial;
        this.fields = fields;
        this.expand = expand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StatusDetailsComponent getTo() {
        return to;
    }

    public Boolean getHasScreen() {
        return hasScreen;
    }

    public Boolean getGlobal() {
        return isGlobal;
    }

    public Boolean getInitial() {
        return isInitial;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public String getExpand() {
        return expand;
    }
}
