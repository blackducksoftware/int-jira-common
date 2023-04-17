/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest;

import java.util.HashSet;
import java.util.Set;

import com.synopsys.integration.jira.common.exception.JiraIntegrationRuntimeException;
import com.synopsys.integration.jira.common.model.JiraPageResponseModel;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.rest.component.IntRestResponse;
import com.synopsys.integration.rest.request.PageRequestHandler;
import com.synopsys.integration.rest.request.Request;

public abstract class JiraCloudPageRequestHandler implements PageRequestHandler {
    private final IntLogger logger;

    public JiraCloudPageRequestHandler(IntLogger logger) {
        this.logger = logger;
    }

    @Override
    public Request createPageRequest(Request.Builder requestBuilder, int offset, int limit) {
        Request request = requestBuilder.build();
        Set<String> limitValue = new HashSet<>();
        limitValue.add(String.valueOf(limit));

        Set<String> offsetValue = new HashSet<>();
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
            throw new JiraIntegrationRuntimeException(errorMessage, e);
        }
    }

}
