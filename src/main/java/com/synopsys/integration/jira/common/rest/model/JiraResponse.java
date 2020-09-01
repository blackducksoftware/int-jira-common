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
package com.synopsys.integration.jira.common.rest.model;

import com.synopsys.integration.rest.RestConstants;
import com.synopsys.integration.util.Stringable;

public class JiraResponse extends Stringable {
    private final int statusCode;
    private final String statusMessage;
    private final String content;

    public JiraResponse(int statusCode, String statusMessage, String content) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getContent() {
        return content;
    }

    public boolean isStatusCodeSuccess() {
        return statusCode >= RestConstants.OK_200 && statusCode < RestConstants.MULT_CHOICE_300;
    }

    public boolean isStatusCodeError() {
        return statusCode >= RestConstants.BAD_REQUEST_400;
    }
}
