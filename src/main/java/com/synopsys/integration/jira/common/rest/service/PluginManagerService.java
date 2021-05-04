/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.service;

import java.util.Map;
import java.util.Optional;

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
    private final JiraApiClient jiraApiClient;

    public PluginManagerService(Gson gson, JiraApiClient jiraApiClient) {
        this.gson = gson;
        this.jiraApiClient = jiraApiClient;
    }

    public Optional<PluginResponseModel> getInstalledApp(String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + appKey + "-key");
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(apiUri);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_PLUGIN);

        try {
            PluginResponseModel pluginComponent = jiraApiClient.get(requestBuilder.build(), PluginResponseModel.class);
            return Optional.of(pluginComponent);
        } catch (IntegrationRestException e) {
            if (404 != e.getHttpStatusCode()) {
                throw e;
            }
        }
        return Optional.empty();
    }

    public boolean isAppInstalled(String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + appKey + "-key");
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(apiUri);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_PLUGIN);

        try {
            // The response should be 404 if the App is not installed
            JiraResponse response = jiraApiClient.get(requestBuilder.build());
            return response.isStatusCodeSuccess();
        } catch (IntegrationRestException e) {
            if (404 != e.getHttpStatusCode()) {
                throw e;
            }
            return false;
        }
    }

    public InstalledAppsResponseModel getInstalledApps() throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createBaseRequestUrl());
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(httpUrl);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_INSTALLED);

        return jiraApiClient.get(requestBuilder.build(), InstalledAppsResponseModel.class);
    }

    public int installMarketplaceCloudApp(String addonKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + "apps/install-subscribe");
        String pluginToken = retrievePluginToken();
        JiraRequest request = createMarketplaceInstallRequest(apiUri, pluginToken, addonKey);
        return jiraApiClient.executeReturnStatus(request);
    }

    public int installMarketplaceServerApp(String addonKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl());
        String pluginToken = retrievePluginToken();
        AvailableAppResponseModel availableApp = getAvailableApp(apiUri.string(), addonKey);
        String pluginUri = availableApp.getBinaryLink().orElse("");
        JiraRequest request = createAppUploadRequest(apiUri, pluginToken, availableApp.getName(), pluginUri);
        return jiraApiClient.executeReturnStatus(request);
    }

    public int installDevelopmentApp(String pluginName, String pluginUri) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl());
        String pluginToken = retrievePluginToken();
        JiraRequest request = createAppUploadRequest(apiUri, pluginToken, pluginName, pluginUri);
        return jiraApiClient.executeReturnStatus(request);
    }

    public int uninstallApp(String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(createBaseRequestUrl() + appKey + "-key");
        String pluginToken = retrievePluginToken();
        JiraRequest request = createDeleteRequest(apiUri, pluginToken);
        return jiraApiClient.executeReturnStatus(request);
    }

    public String retrievePluginToken() throws IntegrationException {
        HttpUrl httpUrl = new HttpUrl(createBaseRequestUrl());
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(httpUrl);
        requestBuilder.addQueryParameter(QUERY_KEY_OS_AUTH_TYPE, QUERY_VALUE_OS_AUTH_TYPE);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_INSTALLED);
        Map<String, String> response = jiraApiClient.getResponseHeaders(requestBuilder.build());
        return response.get("upm-token");
    }

    private AvailableAppResponseModel getAvailableApp(String path, String appKey) throws IntegrationException {
        HttpUrl apiUri = new HttpUrl(path + "available/" + appKey + "-key");
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(apiUri);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_AVAILABLE);
        return jiraApiClient.get(requestBuilder.build(), AvailableAppResponseModel.class);
    }

    private JiraRequest createMarketplaceInstallRequest(HttpUrl apiUri, String pluginToken, String addonKey) {
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(apiUri);
        requestBuilder.addQueryParameter("addonKey", addonKey);
        requestBuilder.addQueryParameter(TOKEN_QUERY, pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, PluginManagerService.MEDIA_TYPE_REMOTE_INSTALL);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_WILDCARD);
        return requestBuilder.build();
    }

    private JiraRequest createAppUploadRequest(HttpUrl apiUri, String pluginToken, String pluginName, String pluginUri) {
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(apiUri);
        requestBuilder.addQueryParameter(TOKEN_QUERY, pluginToken);
        requestBuilder.method(HttpMethod.POST);
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_INSTALL_URI);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_DEFAULT);
        requestBuilder.bodyContent(createBodyContent(pluginName, pluginUri));
        return requestBuilder.build();
    }

    private JiraRequest createDeleteRequest(HttpUrl apiUri, String pluginToken) {
        JiraRequest.Builder requestBuilder = new JiraRequest.Builder(apiUri);
        requestBuilder.addQueryParameter(TOKEN_QUERY, pluginToken);
        requestBuilder.method(HttpMethod.DELETE);
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, MEDIA_TYPE_DEFAULT);
        requestBuilder.addHeader(ACCEPT_HEADER, MEDIA_TYPE_DEFAULT);
        return requestBuilder.build();
    }

    private String createBodyContent(String pluginName, String pluginUri) {
        AppUploadRequestModel uploadRequestModel = new AppUploadRequestModel(pluginUri, pluginName);
        String uploadRequestJson = gson.toJson(uploadRequestModel);
        return uploadRequestJson;
    }

    private String getBaseUrl() {
        String url = jiraApiClient.getBaseUrl();
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    private String createBaseRequestUrl() {
        return getBaseUrl() + PluginManagerService.API_PATH;
    }
}
