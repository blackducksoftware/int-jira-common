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
package com.synopsys.integration.jira.common.rest;

import java.util.HashSet;
import java.util.Set;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.component.IntRestResponse;
import com.synopsys.integration.rest.request.PageRequestHandler;
import com.synopsys.integration.rest.request.Request;

public abstract class JiraCloudPageRequestHandler implements PageRequestHandler {
    private final IntLogger logger;

    public JiraCloudPageRequestHandler(final IntLogger logger) {
        this.logger = logger;
    }

    @Override
    public Request createPageRequest(Request.Builder requestBuilder, int offset, int limit) {
        final Request request = requestBuilder.build();
        final Set<String> limitValue = new HashSet<>();
        limitValue.add(String.valueOf(limit));

        final Set<String> offsetValue = new HashSet<>();
        offsetValue.add(String.valueOf(offset));

        request.getQueryParameters().put("limit", limitValue);
        request.getQueryParameters().put("offset", offsetValue);
        return request;
    }

    @Override
    public <R extends IntRestResponse> int getTotalResponseCount(R response) {
        return castToJiraPageResponseModel(response)
                   .getMaxResults();
    }

    @Override
    public <R extends IntRestResponse> int getCurrentResponseCount(R response) {
        return castToJiraPageResponseModel(response)
                   .getTotal();
    }

    private <R extends IntRestResponse> JiraPageResponseModel castToJiraPageResponseModel(R response) {
        try {
            return (JiraPageResponseModel) response;
        } catch (ClassCastException e) {
            String errorMessage = "The response was not of type " + JiraPageResponseModel.class.getName();
            logger.error(errorMessage);
            IntegrationException integrationException = new IntegrationException(errorMessage, e);
            throw new RuntimeException(integrationException);
        }
    }

}
