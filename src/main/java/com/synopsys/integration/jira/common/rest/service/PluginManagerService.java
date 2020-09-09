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
package com.synopsys.integration.jira.common.rest.service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.AppUploadRequestModel;
import com.synopsys.integration.jira.common.model.response.AvailableAppResponseModel;
import com.synopsys.integration.jira.common.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.model.response.PluginResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.rest.RestConstants;
import com.synopsys.integration.rest.exception.IntegrationRestException;

public class PluginManagerService {
    public static final String ACCEPT_HEADER = "Accept";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String TOKEN_QUERY = "token";

    public static final String API_PATH = "/rest/plugins/1.0/";

    private static final String QUERY_KEY_OS_AUTH_TYPE = "os_authType";
    private static final String QUERY_VALUE_OS_AUTH_TYPE = "basic";

    private static final String MEDIA_TYPE_PREFIX = "application/vnd.atl.plugins";
    private static final String MEDIA_TYPE_SUFFIX = "+json";
    private static final String MEDIA_TYPE_DEFAULT = "application/json";
    private static final String MEDIA_TYPE_WILDCARD = "*/*";

    private static final String MEDIA_TYPE_PLUGIN = MEDIA_TYPE_PREFIX + ".plugin" + MEDIA_TYPE_SUFFIX;
    private static final String MEDIA_TYPE_INSTALLED = MEDIA_TYPE_PREFIX + ".installed" + MEDIA_TYPE_SUFFIX;
    private static final String MEDIA_TYPE_REMOTE_INSTALL = MEDIA_TYPE_PREFIX + ".remote.install" + MEDIA_TYPE_SUFFIX;
    private static final String MEDIA_TYPE_INSTALL_URI = MEDIA_TYPE_PREFIX + ".install.uri" + MEDIA_TYPE_SUFFIX;
    private static final String MEDIA_TYPE_AVAILABLE = MEDIA_TYPE_PREFIX + ".available" + MEDIA_TYPE_SUFFIX;

    private final Gson gson;
    private final JiraService jiraService;

    public PluginManagerService(Gson gson, JiraService jiraService) {
        this.gson = gson;
        this.jiraService = jiraService;
    }

    public Optional<PluginResponseModel> getInstalledApp(String username, String accessTokenOrPassword, String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + appKey + "-key");
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_PLUGIN);

        try {
            PluginResponseModel pluginComponent = jiraService.get(requestBuilder.build(), PluginResponseModel.class);
            return Optional.of(pluginComponent);
        } catch (IntegrationRestException e) {
            if (404 != e.getHttpStatusCode()) {
                throw e;
            }
        }
        return Optional.empty();
    }

    public boolean isAppInstalled(String username, String accessTokenOrPassword, String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + appKey + "-key");
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_PLUGIN);

        JiraResponse response = jiraService.get(requestBuilder.build());
        // The response should be 404 if the App is not installed
        if (response.isStatusCodeError() && RestConstants.NOT_FOUND_404 != response.getStatusCode()) {
            throw new IntegrationRestException(response.getStatusCode(), response.getStatusMessage(), response.getContent(), "There was an error when checking if the app was installed.");
        }
        return response.isStatusCodeSuccess();
    }

    public InstalledAppsResponseModel getInstalledApps(String username, String accessTokenOrPassword) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createBaseRequestUrl());
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(httpUrl, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_INSTALLED);

        return jiraService.get(requestBuilder.build(), InstalledAppsResponseModel.class);
    }

    public int installMarketplaceCloudApp(String addonKey, String username, String accessToken) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + "apps/install-subscribe");
        String pluginToken = retrievePluginToken(username, accessToken);
        JiraRequest request = createMarketplaceInstallRequest(apiUri, username, accessToken, pluginToken, addonKey);
        return jiraService.executeReturnStatus(request);
    }

    public int installMarketplaceServerApp(String addonKey, String username, String password) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl());
        String pluginToken = retrievePluginToken(username, password);
        AvailableAppResponseModel availableApp = getAvailableApp(apiUri.string(), username, password, addonKey);
        String pluginUri = availableApp.getBinaryLink().orElse("");
        JiraRequest request = createAppUploadRequest(apiUri, username, password, pluginToken, availableApp.getName(), pluginUri);
        return jiraService.executeReturnStatus(request);
    }

    public int installDevelopmentApp(String pluginName, String pluginUri, String username, String accessTokenOrPassword) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl());
        String pluginToken = retrievePluginToken(username, accessTokenOrPassword);
        JiraRequest request = createAppUploadRequest(apiUri, username, accessTokenOrPassword, pluginToken, pluginName, pluginUri);
        return jiraService.executeReturnStatus(request);
    }

    public int uninstallApp(String appKey, String username, String accessTokenOrPassword) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + appKey + "-key");
        String pluginToken = retrievePluginToken(username, accessTokenOrPassword);
        JiraRequest request = createDeleteRequest(apiUri, username, accessTokenOrPassword, pluginToken);
        return jiraService.executeReturnStatus(request);
    }

    public String retrievePluginToken(String username, String accessTokenOrPassword) throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createBaseRequestUrl());
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(httpUrl, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_INSTALLED);
        Map<String, String> response = jiraService.getResponseHeaders(requestBuilder.build());
        return response.get("upm-token");
    }

    private AvailableAppResponseModel getAvailableApp(String path, String username, String password, String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(path + "available/" + appKey + "-key");
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, password);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_AVAILABLE);
        return jiraService.get(requestBuilder.build(), AvailableAppResponseModel.class);
    }

    private JiraRequest createMarketplaceInstallRequest(HttpUrl apiUri, String username, String accessToken, String pluginToken, String addonKey) {
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter("addonKey", addonKey);
        requestBuilder.addQueryParameter(TOKEN_QUERY, pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, PluginManagerService.MEDIA_TYPE_REMOTE_INSTALL);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_WILDCARD);
        return requestBuilder.build();
    }

    private JiraRequest createAppUploadRequest(HttpUrl apiUri, String username, String accessTokenOrPassword, String pluginToken, String pluginName, String pluginUri) {
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(TOKEN_QUERY, pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_INSTALL_URI);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_DEFAULT);
        requestBuilder.bodyContent(createBodyContent(pluginName, pluginUri));
        return requestBuilder.build();
    }

    private JiraRequest createDeleteRequest(HttpUrl apiUri, String username, String accessTokenOrPassword, String pluginToken) {
        JiraRequest.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(TOKEN_QUERY, pluginToken);
        requestBuilder.method(HttpMethod.DELETE);
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_DEFAULT);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_DEFAULT);
        return requestBuilder.build();
    }

    private JiraRequest.Builder createBasicRequestBuilder(HttpUrl apiUri, String username, String accessTokenOrPassword) {
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder();

        requestBuilder.url(apiUri);
        byte[] authorizationBytes = String.format("%s:%s", username, accessTokenOrPassword).getBytes(StandardCharsets.UTF_8);
        String authorization = String.format("Basic %s", Base64.encodeBase64String(authorizationBytes));
        requestBuilder.addHeader("authorization", authorization);
        return requestBuilder;
    }

    private String createBodyContent(String pluginName, String pluginUri) {
        AppUploadRequestModel uploadRequestModel = new AppUploadRequestModel(pluginUri, pluginName);
        String uploadRequestJson = gson.toJson(uploadRequestModel);
        return uploadRequestJson;
    }

    private String getBaseUrl() {
        String url = jiraService.getBaseUrl();
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    private String createBaseRequestUrl() {
        return getBaseUrl() + PluginManagerService.API_PATH;
    }
}
