/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import com.synopsys.integration.builder.Buildable;
import com.synopsys.integration.builder.BuilderStatus;
import com.synopsys.integration.builder.IntegrationBuilder;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.util.Stringable;

public class JiraRequest extends Stringable implements Buildable {
    public static final String DEFAULT_ACCEPT_MIME_TYPE = ContentType.APPLICATION_JSON.getMimeType();

    private final HttpUrl url;
    private final HttpMethod method;
    private final String acceptMimeType;
    private final Charset bodyEncoding;
    private final Map<String, Set<String>> queryParameters = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private final String bodyContent;

    public JiraRequest(HttpUrl url, HttpMethod method, String acceptMimeType, Charset bodyEncoding, Map<String, Set<String>> queryParameters, Map<String, String> headers, String bodyContent) {
        this.url = url;
        this.method = method;
        this.acceptMimeType = StringUtils.isBlank(acceptMimeType) ? DEFAULT_ACCEPT_MIME_TYPE : acceptMimeType;
        this.bodyEncoding = null == bodyEncoding ? StandardCharsets.UTF_8 : bodyEncoding;
        this.queryParameters.putAll(queryParameters);
        this.headers.putAll(headers);
        this.bodyContent = bodyContent;
    }

    public JiraRequest(JiraRequest.Builder builder) {
        this(builder.url, builder.method, builder.acceptMimeType, builder.bodyEncoding, builder.queryParameters, builder.headers, builder.bodyContent);
    }

    public HttpUrl getUrl() {
        return url;
    }

    public Map<String, Set<String>> getPopulatedQueryParameters() {
        return new HashMap<>(queryParameters);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getAcceptMimeType() {
        return acceptMimeType;
    }

    public Charset getBodyEncoding() {
        return bodyEncoding;
    }

    public Map<String, Set<String>> getQueryParameters() {
        return queryParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public static class Builder extends IntegrationBuilder<JiraRequest> {
        private HttpUrl url;
        private HttpMethod method;
        private String acceptMimeType;
        private Charset bodyEncoding;
        private Map<String, Set<String>> queryParameters = new HashMap<>();
        private Map<String, String> headers = new HashMap<>();
        private String bodyContent;

        public Builder(JiraRequest request) {
            url = request.url;
            method = request.method;
            acceptMimeType = request.acceptMimeType;
            bodyEncoding = request.bodyEncoding;
            queryParameters.putAll(request.queryParameters);
            headers.putAll(request.headers);
            bodyContent = request.bodyContent;
        }

        public Builder() {
            this(null, HttpMethod.GET);
        }

        public Builder(HttpUrl url) {
            this(url, HttpMethod.GET);
        }

        public Builder(HttpUrl url, HttpMethod method) {
            this(url, method, new HashMap<>());
        }

        public Builder(HttpUrl url, HttpMethod method, Map<String, String> headers) {
            this.url = url;
            this.method = method;
            this.headers.putAll(headers);
            acceptMimeType = DEFAULT_ACCEPT_MIME_TYPE;
            bodyEncoding = StandardCharsets.UTF_8;
        }

        @Override
        protected JiraRequest buildWithoutValidation() {
            return new JiraRequest(
                getUrl(),
                getMethod(),
                getAcceptMimeType(),
                getBodyEncoding(),
                getQueryParameters(),
                getHeaders(),
                getBodyContent());
        }

        @Override
        protected void validate(BuilderStatus builderStatus) {
            // currently, all Request instances are valid
        }

        public JiraRequest.Builder url(HttpUrl url) {
            this.url = url;
            return this;
        }

        public JiraRequest.Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public JiraRequest.Builder acceptMimeType(String acceptMimeType) {
            this.acceptMimeType = acceptMimeType;
            return this;
        }

        public JiraRequest.Builder bodyEncoding(Charset bodyEncoding) {
            this.bodyEncoding = bodyEncoding;
            return this;
        }

        public JiraRequest.Builder queryParameters(Map<String, Set<String>> queryParameters) {
            this.queryParameters = queryParameters;
            return this;
        }

        public JiraRequest.Builder addQueryParameter(String key, String value) {
            queryParameters.computeIfAbsent(key, k -> new HashSet<>()).add(value);
            return this;
        }

        public JiraRequest.Builder addQueryParamIfValueNotBlank(String key, @Nullable String value) {
            if (StringUtils.isBlank(value)) {
                addQueryParameter(key, value);
            }
            return this;
        }

        public JiraRequest.Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public JiraRequest.Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public JiraRequest.Builder bodyContent(String bodyContent) {
            this.bodyContent = bodyContent;
            return this;
        }

        public HttpUrl getUrl() {
            return url;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public String getAcceptMimeType() {
            return acceptMimeType;
        }

        public Charset getBodyEncoding() {
            return bodyEncoding;
        }

        public Map<String, Set<String>> getQueryParameters() {
            return queryParameters;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getBodyContent() {
            return bodyContent;
        }
    }
}
