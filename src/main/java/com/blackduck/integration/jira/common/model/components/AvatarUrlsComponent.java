/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.model.components;

import com.blackduck.integration.rest.component.IntRestComponent;
import com.google.gson.annotations.SerializedName;

public class AvatarUrlsComponent extends IntRestComponent {
    @SerializedName("16x16")
    private String sixteen;
    @SerializedName("24x24")
    private String twentyFour;
    @SerializedName("32x32")
    private String thirtyTwo;
    @SerializedName("48x48")
    private String fortyEight;

    public AvatarUrlsComponent() {
    }

    public AvatarUrlsComponent(final String sixteen, final String twentyFour, final String thirtyTwo, final String fortyEight) {
        this.sixteen = sixteen;
        this.twentyFour = twentyFour;
        this.thirtyTwo = thirtyTwo;
        this.fortyEight = fortyEight;
    }

    public String getSixteen() {
        return sixteen;
    }

    public String getTwentyFour() {
        return twentyFour;
    }

    public String getThirtyTwo() {
        return thirtyTwo;
    }

    public String getFortyEight() {
        return fortyEight;
    }

}
