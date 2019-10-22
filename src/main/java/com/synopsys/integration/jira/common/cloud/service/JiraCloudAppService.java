package com.synopsys.integration.jira.common.cloud.service;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.JiraService;
import com.synopsys.integration.jira.common.rest.service.PluginManagerService;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;

public class JiraCloudAppService extends PluginManagerService {

    public JiraCloudAppService(final Gson gson, final JiraHttpClient httpClient, final JiraService jiraService) {
        super(gson, httpClient, jiraService);
    }

    @Override
    protected String createBaseRequestUrl() {
        return getBaseUrl() + PluginManagerService.API_PATH;
    }

    @Override
    public Response installApp(String addonKey, String username, String accessToken) throws IntegrationException {
        String apiUri = createBaseRequestUrl() + "apps/install-subscribe";
        String pluginToken = retrievePluginToken(username, accessToken);
        Request request = createMarketplaceInstallRequest(apiUri, username, accessToken, pluginToken, addonKey);
        return getHttpClient().execute(request);
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

}
