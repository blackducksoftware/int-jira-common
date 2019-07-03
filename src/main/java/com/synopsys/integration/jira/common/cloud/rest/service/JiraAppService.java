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
        requestBuilder.addQueryParameter("os_authType", "basic");
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", "application/vnd.atl.plugins.plugin+json");

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
        requestBuilder.addQueryParameter("os_authType", "basic");
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", "application/vnd.atl.plugins.installed+json");

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
        requestBuilder.addQueryParameter("os_authType", "basic");
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", "application/vnd.atl.plugins.installed+json");
        Response response = httpClient.execute(requestBuilder.build());
        return response.getHeaderValue("upm-token");
    }

    private Request createUploadRequest(String apiUri, String username, String accessToken, String pluginToken, String pluginName, String pluginUri) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addAdditionalHeader("Content-Type", "application/vnd.atl.plugins.install.uri+json");
        requestBuilder.addAdditionalHeader("Accept", "application/json");
        requestBuilder.bodyContent(createBodyContent(pluginName, pluginUri));
        return requestBuilder.build();
    }

    private Request createDeleteRequest(String apiUri, String username, String accessToken, String pluginToken) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.DELETE);
        requestBuilder.addAdditionalHeader("Content-Type", "application/json");
        requestBuilder.addAdditionalHeader("Accept", "application/json");
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
