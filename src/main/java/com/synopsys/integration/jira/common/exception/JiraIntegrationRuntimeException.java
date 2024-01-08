/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.exception;

public class JiraIntegrationRuntimeException extends RuntimeException {
    public JiraIntegrationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiraIntegrationRuntimeException(Throwable cause) {
        super(cause);
    }

}
