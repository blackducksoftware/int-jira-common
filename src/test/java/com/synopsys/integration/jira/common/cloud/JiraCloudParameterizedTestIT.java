package com.synopsys.integration.jira.common.cloud;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Tag;

import com.synopsys.integration.jira.common.IntegrationsTestConstants;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.rest.oauth1a.JiraOauthTestUtility;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

@Tag(IntegrationsTestConstants.INTEGRATION_TEST)
public abstract class JiraCloudParameterizedTestIT {
    public static Collection getParameters() throws InvalidKeySpecException, NoSuchAlgorithmException {
        PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);

        return Arrays.asList(
            JiraCloudServiceTestUtility.createJiraCredentialClient(intLogger),
            JiraOauthTestUtility.createOAuthClient(JiraCloudServiceTestUtility.getEnvBaseUrl(), JiraCloudServiceTestUtility.getOAuthAccessToken()));
    }
}
