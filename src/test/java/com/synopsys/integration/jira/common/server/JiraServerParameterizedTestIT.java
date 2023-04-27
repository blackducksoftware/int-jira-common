package com.synopsys.integration.jira.common.server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Tag;

import com.synopsys.integration.jira.common.IntegrationsTestConstants;
import com.synopsys.integration.jira.common.rest.oauth1a.JiraOauthTestUtility;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;

@Tag(IntegrationsTestConstants.INTEGRATION_TEST)
public abstract class JiraServerParameterizedTestIT {
    public static Collection getParameters() throws InvalidKeySpecException, NoSuchAlgorithmException {
        TestProperties testProperties = TestProperties.loadTestProperties();
        PrintStreamIntLogger intLogger = new PrintStreamIntLogger(System.out, LogLevel.WARN);

        return Arrays.asList(
            JiraServerServiceTestUtility.createJiraCredentialClient(intLogger),
            JiraServerServiceTestUtility.createJiraBearerAuthClient(intLogger),
            JiraOauthTestUtility.createOAuthClient(
                testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_URL),
                testProperties.getProperty(TestPropertyKey.TEST_JIRA_SERVER_OAUTH_ACCESS_TOKEN)
            )
        );
    }
}
