/*
 * int-jira-common
 *
 * Copyright (c) 2025 Black Duck Software, Inc. 
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest;

public enum RestApiVersion {

    VERSION_2(2),
    VERSION_3(3);

    private int version;

    RestApiVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}
