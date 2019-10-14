package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.UserSearchService;

public class UserSearchServiceTest extends JiraServerServiceTest {
    @Test
    public void findUsersByUsernameTest() throws IntegrationException {
        validateConfiguration();

        JiraServerServiceFactory serviceFactory = createServiceFactory();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        Optional<UserDetailsResponseModel> userMatchingName = userSearchService.findUserByUsername("admin");
        assertTrue(userMatchingName.isPresent(), "Expected to find a user with the username 'admin'.");

        Optional<UserDetailsResponseModel> userNotMatchingName = userSearchService.findUserByUsername("totally_bogus_username_meant_to_not_match_anything");
        assertFalse(userNotMatchingName.isPresent(), "Expected not to find a user with a seemingly bogus username.");
    }

}
