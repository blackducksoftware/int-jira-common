package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.jira.common.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;

public class UserServiceTest extends JiraCloudParameterizedTest {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testSearchUser(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        String email = JiraCloudServiceTestUtility.getEnvUserEmail();

        List<UserDetailsResponseModel> users = userSearchService.findUser(email);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(email, users.get(0).getEmailAddress());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testInvalidUser(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        String email = "invalid_user@does_not_exist_abc_123.org";

        List<UserDetailsResponseModel> users = userSearchService.findUser(email);
        assertTrue(users.isEmpty());
    }
}
