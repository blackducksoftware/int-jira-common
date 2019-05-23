/**
 * int-jira-common
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.jira.common.cloud.model;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.jira.common.model.JiraComponent;

public class AvatarUrlsComponent extends JiraComponent {
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
