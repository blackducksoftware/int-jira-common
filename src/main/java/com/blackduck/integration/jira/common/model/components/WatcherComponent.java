/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import java.util.List;

import com.blackduck.integration.rest.component.IntRestComponent;

public class WatcherComponent extends IntRestComponent {
    private String self;
    private Boolean isWatching;
    private Integer watchCount;
    private List<UserDetailsComponent> watchers;

    public WatcherComponent() {
    }

    public WatcherComponent(final String self, final Boolean isWatching, final Integer watchCount, final List<UserDetailsComponent> watchers) {
        this.self = self;
        this.isWatching = isWatching;
        this.watchCount = watchCount;
        this.watchers = watchers;
    }

    public String getSelf() {
        return self;
    }

    public Boolean getWatching() {
        return isWatching;
    }

    public Integer getWatchCount() {
        return watchCount;
    }

    public List<UserDetailsComponent> getWatchers() {
        return watchers;
    }

}
