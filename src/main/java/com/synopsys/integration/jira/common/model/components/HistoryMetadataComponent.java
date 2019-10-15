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

public class HistoryMetadataComponent extends IntRestComponent {
    private String type;
    private String description;
    private String descriptionKey;
    private String activityDescription;
    private String activityDescriptionKey;
    private String emailDescription;
    private String emailDescriptionKey;
    private Object actor; // TODO
    private Object generator; // TODO
    private Object cause; // TODO
    private Object extraData; // TODO

    public HistoryMetadataComponent() {
    }

    public HistoryMetadataComponent(final String type, final String description, final String descriptionKey, final String activityDescription, final String activityDescriptionKey, final String emailDescription,
        final String emailDescriptionKey, final Object actor,
        final Object generator, final Object cause, final Object extraData) {
        this.type = type;
        this.description = description;
        this.descriptionKey = descriptionKey;
        this.activityDescription = activityDescription;
        this.activityDescriptionKey = activityDescriptionKey;
        this.emailDescription = emailDescription;
        this.emailDescriptionKey = emailDescriptionKey;
        this.actor = actor;
        this.generator = generator;
        this.cause = cause;
        this.extraData = extraData;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public String getActivityDescriptionKey() {
        return activityDescriptionKey;
    }

    public String getEmailDescription() {
        return emailDescription;
    }

    public String getEmailDescriptionKey() {
        return emailDescriptionKey;
    }

    public Object getActor() {
        return actor;
    }

    public Object getGenerator() {
        return generator;
    }

    public Object getCause() {
        return cause;
    }

    public Object getExtraData() {
        return extraData;
    }

}
