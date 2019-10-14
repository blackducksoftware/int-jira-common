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
package com.synopsys.integration.jira.common.server.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.request.JiraCloudRequestFactory;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraService;
import com.synopsys.integration.rest.exception.IntegrationRestException;
import com.synopsys.integration.rest.request.Request;

public class UserSearchService {
    public static final String API_PATH = "/rest/api/2/user";

    private JiraService jiraService;

    public UserSearchService(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    public Optional<UserDetailsResponseModel> findUserByUsername(String username) throws IntegrationException {
        return findUsersByQuery("username", username);
    }

    public Optional<UserDetailsResponseModel> findUserByUserKey(String userKey) throws IntegrationException {
        return findUsersByQuery("key", userKey);
    }

    private Optional<UserDetailsResponseModel> findUsersByQuery(String queryKey, String queryValue) throws IntegrationException {
        if (StringUtils.isBlank(queryValue)) {
            return Optional.empty();
        }

        String uri = createApiUri();
        Request request = JiraCloudRequestFactory.createDefaultBuilder()
                              .uri(uri)
                              .addQueryParameter(queryKey, queryValue)
                              .build();
        try {
            UserDetailsResponseModel userDetailsResponseModel = jiraService.get(request, UserDetailsResponseModel.class);
            return Optional.of(userDetailsResponseModel);
        } catch (IntegrationRestException e) {
            if (404 != e.getHttpStatusCode()) {
                throw e;
            }
        }
        return Optional.empty();
    }

    private String createApiUri() {
        return jiraService.getBaseUrl() + API_PATH;
    }

}
