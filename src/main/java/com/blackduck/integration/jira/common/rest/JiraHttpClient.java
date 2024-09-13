/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.rest;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.rest.model.JiraRequest;
import com.blackduck.integration.jira.common.rest.model.JiraResponse;

public interface JiraHttpClient {
    String getBaseUrl();

    JiraResponse execute(JiraRequest jiraRequest) throws IntegrationException;
}
