/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.components;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.rest.component.IntRestComponent;

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
