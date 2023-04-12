/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.AvatarUrlsComponent;

public class UserDetailsResponseModel extends JiraResponseModel {
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

    public UserDetailsResponseModel() {
    }

    public UserDetailsResponseModel(
        String self,
        String name,
        String key,
        String accountId,
        String emailAddress,
        AvatarUrlsComponent avatarUrls,
        String displayName,
        Boolean active,
        String timeZone,
        String accountType
    ) {
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
