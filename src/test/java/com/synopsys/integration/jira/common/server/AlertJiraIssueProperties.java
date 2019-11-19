/**
 * blackduck-alert
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
package com.synopsys.integration.jira.common.server;

import java.io.Serializable;

import com.synopsys.integration.util.Stringable;

public class AlertJiraIssueProperties extends Stringable implements Serializable {
    private String provider;
    private String topicName;
    private String topicValue;
    private String subTopicName;
    private String subTopicValue;
    private String category;
    private String componentName;
    private String componentValue;
    private String subComponentName;
    private String subComponentValue;
    private String additionalKey;

    public AlertJiraIssueProperties() {
        // For serialization
    }

    public AlertJiraIssueProperties(
        String provider,
        String topicName,
        String topicValue,
        String subTopicName,
        String subTopicValue,
        String category,
        String componentName,
        String componentValue,
        String subComponentName,
        String subComponentValue,
        String additionalKey
    ) {
        this.provider = provider;
        this.topicName = topicName;
        this.topicValue = topicValue;
        this.subTopicName = subTopicName;
        this.subTopicValue = subTopicValue;
        this.category = category;
        this.componentName = componentName;
        this.componentValue = componentValue;
        this.subComponentName = subComponentName;
        this.subComponentValue = subComponentValue;
        this.additionalKey = additionalKey;
    }

    public String getProvider() {
        return provider;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicValue() {
        return topicValue;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public String getSubTopicValue() {
        return subTopicValue;
    }

    public String getCategory() {
        return category;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public String getSubComponentName() {
        return subComponentName;
    }

    public String getSubComponentValue() {
        return subComponentValue;
    }

    public String getAdditionalKey() {
        return additionalKey;
    }

}
