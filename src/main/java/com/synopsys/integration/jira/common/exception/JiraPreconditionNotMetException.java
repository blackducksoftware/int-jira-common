/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.exception;

import com.synopsys.integration.exception.IntegrationException;

public class JiraPreconditionNotMetException extends IntegrationException {
    public JiraPreconditionNotMetException(String message) {
        super(message);
    }

}
