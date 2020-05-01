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

import java.util.Optional;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.AppUploadRequestModel;
import com.synopsys.integration.jira.common.model.response.AvailableAppResponseModel;
import com.synopsys.integration.jira.common.model.response.InstalledAppsResponseModel;
import com.synopsys.integration.jira.common.model.response.PluginResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.RestConstants;
import com.synopsys.integration.rest.body.StringBodyContent;
import com.synopsys.integration.rest.exception.IntegrationRestException;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.response.Response;

public class PluginManagerService {
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

    private Gson gson;
    private JiraHttpClient httpClient;
    private JiraService jiraService;

    public PluginManagerService(Gson gson, JiraHttpClient httpClient, JiraService jiraService) {
        this.gson = gson;
        this.httpClient = httpClient;
        this.jiraService = jiraService;
    }

    public Optional<PluginResponseModel> getInstalledApp(String username, String accessTokenOrPassword, String appKey) throws IntegrationException {
        String apiUri = createBaseRequestUrl() + appKey + "-key";
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_PLUGIN);

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
        String apiUri = createBaseRequestUrl() + appKey + "-key";
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_PLUGIN);

        Response response = jiraService.get(requestBuilder.build());
        // The response should be 404 if the App is not installed
        if (response.isStatusCodeError() && RestConstants.NOT_FOUND_404 != response.getStatusCode()) {
            httpClient.getLogger().debug(String.format("Got error when checking if the App '%s' is installed.", appKey));
            httpClient.getLogger().debug(String.format("Error code: '%s'. Response: '%s'", response.getStatusCode(), response.getContentString()));
        }
        return response.isStatusCodeSuccess();
    }

    public InstalledAppsResponseModel getInstalledApps(String username, String accessTokenOrPassword) throws IntegrationException {
        Request.Builder requestBuilder = createBasicRequestBuilder(createBaseRequestUrl(), username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_INSTALLED);

        return jiraService.get(requestBuilder.build(), InstalledAppsResponseModel.class);
    }

    public Response installMarketplaceCloudApp(String addonKey, String username, String accessToken) throws IntegrationException {
        String apiUri = createBaseRequestUrl() + "apps/install-subscribe";
        String pluginToken = retrievePluginToken(username, accessToken);
        Request request = createMarketplaceInstallRequest(apiUri, username, accessToken, pluginToken, addonKey);
        return httpClient.execute(request);
    }

    public Response installMarketplaceServerApp(String addonKey, String username, String password) throws IntegrationException {
        String apiUri = createBaseRequestUrl();
        String pluginToken = retrievePluginToken(username, password);
        AvailableAppResponseModel availableApp = getAvailableApp(apiUri, username, password, addonKey);
        String pluginUri = availableApp.getBinaryLink().orElse("");
        Request request = createAppUploadRequest(apiUri, username, password, pluginToken, availableApp.getName(), pluginUri);
        return httpClient.execute(request);
    }

    public Response installDevelopmentApp(String pluginName, String pluginUri, String username, String accessTokenOrPassword) throws IntegrationException {
        String apiUri = createBaseRequestUrl();
        String pluginToken = retrievePluginToken(username, accessTokenOrPassword);
        Request request = createAppUploadRequest(apiUri, username, accessTokenOrPassword, pluginToken, pluginName, pluginUri);
        return httpClient.execute(request);
    }

    public Response uninstallApp(String appKey, String username, String accessTokenOrPassword) throws IntegrationException {
        String apiUri = createBaseRequestUrl();
        String pluginToken = retrievePluginToken(username, accessTokenOrPassword);
        Request request = createDeleteRequest(apiUri + appKey + "-key", username, accessTokenOrPassword, pluginToken);
        return httpClient.execute(request);
    }

    public String retrievePluginToken(String username, String accessTokenOrPassword) throws IntegrationException {
        Request.Builder requestBuilder = createBasicRequestBuilder(createBaseRequestUrl(), username, accessTokenOrPassword);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_INSTALLED);
        Response response = httpClient.execute(requestBuilder.build());
        return response.getHeaderValue("upm-token");
    }

    private AvailableAppResponseModel getAvailableApp(String path, String username, String password, String appKey) throws IntegrationException {
        String apiUri = path + "available/" + appKey + "-key";
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, password);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_AVAILABLE);
        return jiraService.get(requestBuilder.build(), AvailableAppResponseModel.class);
    }

    private Request createMarketplaceInstallRequest(String apiUri, String username, String accessToken, String pluginToken, String addonKey) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessToken);
        requestBuilder.addQueryParameter("addonKey", addonKey);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addAdditionalHeader("Content-Type", PluginManagerService.MEDIA_TYPE_REMOTE_INSTALL);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_WILDCARD);
        return requestBuilder.build();
    }

    private Request createAppUploadRequest(String apiUri, String username, String accessTokenOrPassword, String pluginToken, String pluginName, String pluginUri) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addAdditionalHeader("Content-Type", MEDIA_TYPE_INSTALL_URI);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_DEFAULT);
        requestBuilder.bodyContent(createBodyContent(pluginName, pluginUri));
        return requestBuilder.build();
    }

    private Request createDeleteRequest(String apiUri, String username, String accessTokenOrPassword, String pluginToken) {
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, accessTokenOrPassword);
        requestBuilder.addQueryParameter("token", pluginToken);
        requestBuilder.method(HttpMethod.DELETE);
        requestBuilder.addAdditionalHeader("Content-Type", MEDIA_TYPE_DEFAULT);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_DEFAULT);
        return requestBuilder.build();
    }

    private Request.Builder createBasicRequestBuilder(String apiUri, String username, String accessTokenOrPassword) {
        Request.Builder requestBuilder = Request.newBuilder();

        requestBuilder.uri(apiUri);
        byte[] authorizationBytes = String.format("%s:%s", username, accessTokenOrPassword).getBytes(Charsets.UTF_8);
        String authorization = String.format("Basic %s", Base64.encodeBase64String(authorizationBytes));
        requestBuilder.addAdditionalHeader("authorization", authorization);
        return requestBuilder;
    }

    private StringBodyContent createBodyContent(String pluginName, String pluginUri) {
        AppUploadRequestModel uploadRequestModel = new AppUploadRequestModel(pluginUri, pluginName);
        String uploadRequestJson = gson.toJson(uploadRequestModel);
        return new StringBodyContent(uploadRequestJson);
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
