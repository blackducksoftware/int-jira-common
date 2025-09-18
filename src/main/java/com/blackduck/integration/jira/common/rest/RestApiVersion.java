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
