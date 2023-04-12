/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.cloud.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraRequestFactory;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.service.JiraApiClient;
import com.synopsys.integration.rest.HttpUrl;

/*
 * Brian Mandel: There is an existing issue with OAuth returning a User object with the email field always blank.
 * Waiting on Atlassian support to get back to us about why this is the case.
 */
public class UserSearchService {
    public static final String API_PATH_SEARCH = "/rest/api/2/user/search";
    public static final String API_PATH_CURRENT = "/rest/api/2/myself";

    private final JiraApiClient jiraCloudService;

    public UserSearchService(JiraApiClient jiraCloudService) {
        this.jiraCloudService = jiraCloudService;
    }

    public List<UserDetailsResponseModel> findUser(String queryValue) throws IntegrationException {
        if (StringUtils.isBlank(queryValue)) {
            return Collections.emptyList();
        }

        HttpUrl uri = createSearchUri();
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(uri)
                                  .addQueryParameter("query", queryValue)
                                  .build();
        return jiraCloudService.getList(request, UserDetailsResponseModel.class);
    }

    public UserDetailsResponseModel getCurrentUser() throws IntegrationException {
        JiraRequest jiraRequest = JiraRequestFactory.createDefaultGetRequest(createCurrentUri());
        return jiraCloudService.get(jiraRequest, UserDetailsResponseModel.class);
    }

    private HttpUrl createSearchUri() throws IntegrationException {
        return new HttpUrl(jiraCloudService.getBaseUrl() + API_PATH_SEARCH);
    }

    private HttpUrl createCurrentUri() throws IntegrationException {
        return new HttpUrl(jiraCloudService.getBaseUrl() + API_PATH_CURRENT);
    }
}
