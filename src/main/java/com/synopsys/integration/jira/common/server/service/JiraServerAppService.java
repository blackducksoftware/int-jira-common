package com.synopsys.integration.jira.common.server.service;

import com.google.gson.Gson;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.AvailableAppResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.service.JiraService;
import com.synopsys.integration.jira.common.rest.service.PluginManagerService;
import com.synopsys.integration.rest.HttpMethod;
import com.synopsys.integration.rest.request.Request;
import com.synopsys.integration.rest.request.Response;

public class JiraServerAppService extends PluginManagerService {

    public JiraServerAppService(final Gson gson, final JiraHttpClient httpClient, final JiraService jiraService) {
        super(gson, httpClient, jiraService);
    }

    @Override
    protected String createBaseRequestUrl() {
        String baseUrl = getBaseUrl();
        if (baseUrl.endsWith("/jira")) {
            return baseUrl + PluginManagerService.API_PATH;
        }
        return baseUrl + "/jira" + PluginManagerService.API_PATH;
    }

    @Override
    public Response installApp(String addonKey, String username, String password) throws IntegrationException {
        String apiUri = createBaseRequestUrl();
        String pluginToken = retrievePluginToken(username, password);
        AvailableAppResponseModel availableApp = getAvailableApp(apiUri, username, password, addonKey);
        String pluginUri = availableApp.getBinaryLink().orElse("");
        Request request = createAppUploadRequest(apiUri, username, password, pluginToken, availableApp.getName(), pluginUri);
        return getHttpClient().execute(request);
    }

    private AvailableAppResponseModel getAvailableApp(String path, String username, String password, String appKey) throws IntegrationException {
        String apiUri = path + "available/" + appKey + "-key";
        Request.Builder requestBuilder = createBasicRequestBuilder(apiUri, username, password);
        requestBuilder.method(HttpMethod.GET);
        requestBuilder.addAdditionalHeader("Accept", MEDIA_TYPE_AVAILABLE);
        return getJiraService().get(requestBuilder.build(), AvailableAppResponseModel.class);
    }
}
