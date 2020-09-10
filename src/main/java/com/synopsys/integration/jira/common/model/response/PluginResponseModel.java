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
package com.synopsys.integration.jira.common.model.response;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class PluginResponseModel extends JiraResponseModel {
    private Boolean enabled;
    private JsonObject links;
    private String name;
    private String version;
    private Boolean userInstalled;
    private Boolean optional;
    @SerializedName("static")
    private Boolean isStatic;
    private Boolean unloadable;
    private String description;
    private String key;
    private Boolean usesLicensing;
    private Boolean remotable;
    private JsonObject vendor;

    public PluginResponseModel() {
    }

    public PluginResponseModel(Boolean enabled, JsonObject links, String name, String version, Boolean userInstalled, Boolean optional, Boolean isStatic, Boolean unloadable,
        String description, String key, Boolean usesLicensing,
        Boolean remotable, JsonObject vendor) {
        this.enabled = enabled;
        this.links = links;
        this.name = name;
        this.version = version;
        this.userInstalled = userInstalled;
        this.optional = optional;
        this.isStatic = isStatic;
        this.unloadable = unloadable;
        this.description = description;
        this.key = key;
        this.usesLicensing = usesLicensing;
        this.remotable = remotable;
        this.vendor = vendor;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public JsonObject getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Boolean getUserInstalled() {
        return userInstalled;
    }

    public Boolean getOptional() {
        return optional;
    }

    public Boolean getStatic() {
        return isStatic;
    }

    public Boolean getUnloadable() {
        return unloadable;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public Boolean getUsesLicensing() {
        return usesLicensing;
    }

    public Boolean getRemotable() {
        return remotable;
    }

    public JsonObject getVendor() {
        return vendor;
    }

}
