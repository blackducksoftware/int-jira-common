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

import com.synopsys.integration.rest.component.IntRestComponent;

public class UserDetailsComponent extends IntRestComponent {
    private String self;
    private String name;
    private String key;
    private String accountId;
    private String emailAddress;
    private AvatarUrlsComponent avatarUrls;
    private String displayName;
    private Boolean active;
    private String timeZone;
    private String accountType;

    public UserDetailsComponent() {
    }

    public UserDetailsComponent(final String self, final String name, final String key, final String accountId, final String emailAddress, final AvatarUrlsComponent avatarUrls, final String displayName, final Boolean active,
        final String timeZone, final String accountType) {
        this.self = self;
        this.name = name;
        this.key = key;
        this.accountId = accountId;
        this.emailAddress = emailAddress;
        this.avatarUrls = avatarUrls;
        this.displayName = displayName;
        this.active = active;
        this.timeZone = timeZone;
        this.accountType = accountType;
    }

    public String getSelf() {
        return self;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public AvatarUrlsComponent getAvatarUrls() {
        return avatarUrls;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getActive() {
        return active;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getAccountType() {
        return accountType;
    }

}
