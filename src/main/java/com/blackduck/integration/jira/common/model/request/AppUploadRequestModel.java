/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.request;

public class AppUploadRequestModel extends JiraRequestModel {
    private String pluginUri;
    private String pluginName;

    public AppUploadRequestModel(final String pluginUri, final String pluginName) {
        this.pluginUri = pluginUri;
        this.pluginName = pluginName;
    }

    public String getPluginUri() {
        return pluginUri;
    }

    public String getPluginName() {
        return pluginName;
    }

}
