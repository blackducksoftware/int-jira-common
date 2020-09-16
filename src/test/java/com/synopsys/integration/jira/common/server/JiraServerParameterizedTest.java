package com.synopsys.integration.jira.common.server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;

import com.synopsys.integration.jira.common.rest.oauth1a.JiraOauthTestUtility;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

public class JiraServerParameterizedTest {
    public static Collection getParameters() throws InvalidKeySpecException, NoSuchAlgorithmException {
        PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);

        return Arrays.asList(
            JiraServerServiceTestUtility.createJiraCredentialClient(intLogger),
            JiraOauthTestUtility.createOAuthClient());
    }
}
