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
package com.synopsys.integration.jira.common.cloud.rest.service;

import java.util.Optional;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.model.request.AppUploadRequestModel;
import com.synopsys.integration.jira.common.cloud.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.PluginResponseModel;
import com.synopsys.integration.jira.common.cloud.rest.JiraCloudHttpClient;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.body.StringBodyContent;
import com.synopsys.integration.rest.exception.IntegrationRestException;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;

public class JiraAppService {
    public static final String API_PATH = "/rest/plugins/1.0/";

    private static final String QUERY_KEY_OS_AUTH_TYPE = "os_authType";
    private static final String QUERY_VALUE_OS_AUTH_TYPE = "basic";

    private static final String MEDIA_TYPE_PREFIX = "application/vnd.atl.plugins";
    private static final String MEDIA_TYPE_SUFFIX = "+json";
    private static final String MEDIA_TYPE_DEFAULT = "application/json";

    private static final String MEDIA_TYPE_PLUGIN = MEDIA_TYPE_PREFIX + ".plugin" + MEDIA_TYPE_SUFFIX;
    private static final String MEDIA_TYPE_INSTALLED = MEDIA_TYPE_PREFIX + ".installed" + MEDIA_TYPE_SUFFIX;
    private static final String MEDIA_TYPE_INSTALL_URI = MEDIA_TYPE_PREFIX + ".install.uri" + MEDIA_TYPE_SUFFIX;

    private Gson gson;
    private JiraCloudHttpClient httpClient;
    private JiraCloudService jiraCloudService;

    public JiraAppService(final Gson gson, JiraCloudHttpClient httpClient, final JiraCloudService jiraCloudService) {
        this.gson = gson;
        this.httpClient = httpClient;
        this.jiraCloudService = jiraCloudService;
    }

    public Optional<PluginResponseModel> getInstalledApp(String username, String accessToken, String appKey) throws IntegrationException {
        final String apiUri = getBaseUrl() + API_PATH + appKey + "-key";
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_PLUGIN);

        try {
            final PluginResponseModel pluginComponent = jiraCloudService.get(requestBuilder.build(), PluginResponseModel.class);
            return Optional.of(pluginComponent);
        } catch (IntegrationRestException e) {
            if (404 != e.getHttpStatusCode()) {
                throw e;
            }
        }
        return Optional.empty();
    }

    public InstalledAppsResponseModel getInstalledApps(String username, String accessToken) throws IntegrationException {
        Request.Builder requestBuilder = createBasicRequestBuilder(getBaseUrl() + API_PATH, username, accessToken);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_INSTALLED);

        return jiraCloudService.get(requestBuilder.build(), InstalledAppsResponseModel.class);
    }

    public Response installApp(String pluginName, String pluginUri, String username, String accessToken) throws IntegrationException {
        String apiUri = getBaseUrl() + API_PATH;
        final String pluginToken = retrievePluginToken(username, accessToken);
        final Request request = createUploadRequest(apiUri, username, accessToken, pluginToken, pluginName, pluginUri);
        return httpClient.execute(request);
    }

    public Response uninstallApp(String appKey, String username, String accessToken) throws IntegrationException {
        String apiUri = getBaseUrl() + API_PATH;
        final String pluginToken = retrievePluginToken(username, accessToken);
        final Request request = createDeleteRequest(apiUri + appKey + "-key", username, accessToken, pluginToken);
        return httpClient.execute(request);
    }

    public String retrievePluginToken(String username, String accessToken) throws IntegrationException {
        Request.Builder requestBuilder = createBasicRequestBuilder(getBaseUrl() + API_PATH, username, accessToken);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_INSTALLED);
        Response response = httpClient.execute(requestBuilder.build());
        return response.getHeaderValue("upm-token");
    }

    private Request createUploadRequest(String apiUri, String username, String accessToken, String pluginToken, String pluginName, String pluginUri) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addAdditionalHeader("Content-Type", MEDIA_TYPE_INSTALL_URI);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_DEFAULT);
        requestBuilder.bodyContent(createBodyContent(pluginName, pluginUri));
        return requestBuilder.build();
    }

    private Request createDeleteRequest(String apiUri, String username, String accessToken, String pluginToken) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.DELETE);
        requestBuilder.addAdditionalHeader("Content-Type", MEDIA_TYPE_DEFAULT);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_DEFAULT);
        return requestBuilder.build();
    }

    private Request.Builder createBasicRequestBuilder(String apiUri, String username, String accessToken) {
        Request.Builder requestBuilder = Request.newBuilder();

        requestBuilder.uri(apiUri);
        byte[] authorizationBytes = String.format("%s:%s", username, accessToken).getBytes(Charsets.UTF_8);
        String authorization = String.format("Basic %s", Base64.encodeBase64String(authorizationBytes));
        requestBuilder.addAdditionalHeader("authorization", authorization);
        return requestBuilder;
    }

    private StringBodyContent createBodyContent(String pluginName, String pluginUri) {
        AppUploadRequestModel uploadRequestModel = new AppUploadRequestModel(pluginUri, pluginName);
        final String uploadRequestJson = gson.toJson(uploadRequestModel);
        return new StringBodyContent(uploadRequestJson);
    }

    private String getBaseUrl() {
        String url = jiraCloudService.getBaseUrl();
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

}