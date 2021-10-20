package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.rest.model.JiraRequest;
import com.synopsys.integration.jira.common.rest.model.JiraResponse;
import com.synopsys.integration.jira.common.test.TestProperties;
import com.synopsys.integration.jira.common.test.TestPropertyKey;
import com.synopsys.integration.rest.HttpUrl;

public class JiraCloudHttpClientTestIT extends JiraCloudParameterizedTestIT {
    private static final String RESTRICTED_ENDPOINT_SPEC = "/rest/api/2/field";
    private final TestProperties testProperties = new TestProperties();

    @ParameterizedTest
    @MethodSource("getParameters")
    public void authenticationTest(JiraHttpClient jiraHttpClient) throws IntegrationException, IOException {
        String baseUrl = testProperties.getProperty(TestPropertyKey.TEST_JIRA_CLOUD_URL.getPropertyKey());
        JiraCloudServiceTestUtility.validateConfiguration();

        HttpUrl requestUrl = new HttpUrl(baseUrl + RESTRICTED_ENDPOINT_SPEC);
        JiraRequest request = new JiraRequest.Builder(requestUrl).build();
        JiraResponse response = jiraHttpClient.execute(request);
        assertTrue(response.isStatusCodeSuccess(), "Expected the request to be valid");
    }

}
