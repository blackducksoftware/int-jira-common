package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.UserSearchService;

public class UserSearchServiceTestIT extends JiraServerParameterizedTestIT {
    @ParameterizedTest
    @MethodSource("getParameters")
    public void findUsersByUsernameTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        Optional<UserDetailsResponseModel> userMatchingName = userSearchService.findUserByUsername("admin");
        assertTrue(userMatchingName.isPresent(), "Expected to find a user with the username 'admin'.");

        Optional<UserDetailsResponseModel> userNotMatchingName = userSearchService.findUserByUsername("totally_bogus_username_meant_to_not_match_anything");
        assertFalse(userNotMatchingName.isPresent(), "Expected not to find a user with a seemingly bogus username.");
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getCurrentUserTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        UserDetailsResponseModel currentUser = userSearchService.getCurrentUser();
        assertNotNull(currentUser);
        assertTrue(StringUtils.isNotBlank(currentUser.getSelf()));
    }

}
