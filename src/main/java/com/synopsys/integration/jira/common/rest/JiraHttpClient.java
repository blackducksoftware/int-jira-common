/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;

public interface JiraHttpClient {
    String getBaseUrl();

    JiraResponse execute(JiraRequest jiraRequest) throws IntegrationException;
}
