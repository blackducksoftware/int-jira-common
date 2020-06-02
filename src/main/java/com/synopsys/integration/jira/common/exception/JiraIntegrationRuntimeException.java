package com.synopsys.integration.jira.common.exception;

public class JiraIntegrationRuntimeException extends RuntimeException {
    public JiraIntegrationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiraIntegrationRuntimeException(Throwable cause) {
        super(cause);
    }

}
