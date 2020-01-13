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
package com.synopsys.integration.jira.common.model;

import com.synopsys.integration.jira.common.enumeration.JiraSortOrder;

public class JiraQueryParam {
    private String key;
    private String value;
    private JiraSortOrder jiraSortOrder;

    public JiraQueryParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JiraQueryParam(String key, String value, JiraSortOrder jiraSortOrder) {
        this.key = key;
        this.value = value;
        this.jiraSortOrder = jiraSortOrder;
    }

    public String createQueryString() {
        if (null != jiraSortOrder) {
            return key + "=" + jiraSortOrder.getQueryValuePrefix() + value;
        }
        return key + "=" + value;
    }

}
