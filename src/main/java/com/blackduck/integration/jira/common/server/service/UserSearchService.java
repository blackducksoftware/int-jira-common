/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.server.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.model.request.JiraRequestFactory;
import com.blackduck.integration.jira.common.model.response.UserDetailsResponseModel;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.service.JiraApiClient;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.exception.IntegrationRestException;

/*
 * Brian Mandel: There is an existing issue with OAuth returning a User object with the email field always blank.
 * Waiting on Atlassian support to get back to us about why this is the case.
 */
public class UserSearchService {
    public static final String API_PATH_USER = "/rest/api/2/user";
    public static final String API_PATH_CURRENT = "/rest/api/2/myself";

    private final JiraApiClient jiraApiClient;

    public UserSearchService(JiraApiClient jiraApiClient) {
        this.jiraApiClient = jiraApiClient;
    }

    public Optional<UserDetailsResponseModel> findUserByUsername(String username) throws IntegrationException {
        return findUsersByQuery("username", username);
    }

    public Optional<UserDetailsResponseModel> findUserByUserKey(String userKey) throws IntegrationException {
        return findUsersByQuery("key", userKey);
    }

    public UserDetailsResponseModel getCurrentUser() throws IntegrationException {
        JiraRequest jiraRequest = JiraRequestFactory.createDefaultGetRequest(createCurrentUri());
        return jiraApiClient.get(jiraRequest, UserDetailsResponseModel.class);
    }

    private Optional<UserDetailsResponseModel> findUsersByQuery(String queryKey, String queryValue) throws IntegrationException {
        if (StringUtils.isBlank(queryValue)) {
            return Optional.empty();
        }

        HttpUrl url = createUserUri();
        JiraRequest request = JiraRequestFactory.createDefaultBuilder()
                                  .url(url)
                                  .addQueryParameter(queryKey, queryValue)
                                  .build();
        try {
            UserDetailsResponseModel userDetailsResponseModel = jiraApiClient.get(request, UserDetailsResponseModel.class);
            return Optional.of(userDetailsResponseModel);
        } catch (IntegrationRestException e) {
            if (404 != e.getHttpStatusCode()) {
                throw e;
            }
        }
        return Optional.empty();
    }

    private HttpUrl createUserUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_USER);
    }

    private HttpUrl createCurrentUri() throws IntegrationException {
        return new HttpUrl(jiraApiClient.getBaseUrl() + API_PATH_CURRENT);
    }

}
