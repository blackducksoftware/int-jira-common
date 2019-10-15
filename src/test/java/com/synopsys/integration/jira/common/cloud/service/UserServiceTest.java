package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;

public class UserServiceTest extends JiraCloudServiceTest {

    @Test
    public void testSearchUser() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        String email = getEnvUserEmail();

        List<UserDetailsResponseModel> users = userSearchService.findUser(email);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(email, users.get(0).getEmailAddress());
    }

    @Test
    public void testInvalidUser() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        String email = "invalid_user@does_not_exist_abc_123.org";

        List<UserDetailsResponseModel> users = userSearchService.findUser(email);
        assertTrue(users.isEmpty());
    }
}
