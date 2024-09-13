/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.response;

import java.util.List;

import com.blackduck.integration.jira.common.model.JiraResponseModel;
import com.google.gson.JsonObject;

public class InstalledAppsResponseModel extends JiraResponseModel {
    private List<PluginResponseModel> plugins;
    private JsonObject links;

    public InstalledAppsResponseModel() {
    }

    public InstalledAppsResponseModel(List<PluginResponseModel> plugins, JsonObject links) {
        this.plugins = plugins;
        this.links = links;
    }

    public List<PluginResponseModel> getPlugins() {
        return plugins;
    }

    public JsonObject getLinks() {
        return links;
    }

}
