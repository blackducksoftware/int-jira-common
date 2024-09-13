/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest.model;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.EnglishReasonPhraseCatalog;

import com.blackduck.integration.rest.HttpMethod;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.RestConstants;
import com.blackduck.integration.rest.exception.IntegrationRestException;
import com.blackduck.integration.util.Stringable;

public class JiraResponse extends Stringable {
    private final HttpMethod httpMethod;
    private final HttpUrl httpUrl;
    private final int statusCode;
    private final String statusMessage;
    private final String content;
    private final Map<String, String> headers;

    public JiraResponse(HttpMethod httpMethod, HttpUrl httpUrl, int statusCode, String statusMessage, String content, Map<String, String> headers) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.content = content;
        this.headers = headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isStatusCodeSuccess() {
        return statusCode >= RestConstants.OK_200 && statusCode < RestConstants.MULT_CHOICE_300;
    }

    public boolean isStatusCodeError() {
        return statusCode >= RestConstants.BAD_REQUEST_400;
    }

    public void throwExceptionForError() throws IntegrationRestException {
        if (isStatusCodeError()) {
            int statusCode = getStatusCode();
            String statusMessage = getStatusMessage();

            String statusCodeDescription = EnglishReasonPhraseCatalog.INSTANCE.getReason(statusCode, Locale.ENGLISH);

            String reasonPhraseDescription = "";
            if (StringUtils.isNotBlank(statusMessage)) {
                reasonPhraseDescription = String.format(", reason phrase was %s", statusMessage);
            }

            String messageFormat = "There was a problem trying to request data, response was %s %s%s.";
            String message = String.format(messageFormat, statusCode, statusCodeDescription, reasonPhraseDescription);
            throw new IntegrationRestException(getHttpMethod(), getHttpUrl(), statusCode, statusMessage, getContent(), message);
        }
    }
}
