package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.model.CustomFieldPageResponseModel;
import com.synopsys.integration.jira.common.server.service.CustomFieldService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

public class CustomFieldServiceTestIT extends JiraServerParameterizedTestIT {
    @ParameterizedTest
    @MethodSource("getParameters")
    public void getCustomFieldsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        CustomFieldService customFieldService = serviceFactory.createCustomFieldService();

        CustomFieldPageResponseModel customFields = customFieldService.getCustomFields();
        assertNotNull(customFields.getValues(), "Expected values to be an empty list rather than null");
        assertEquals(customFields.getTotal(), customFields.getValues().size());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getCustomFieldsPageTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        CustomFieldService customFieldService = serviceFactory.createCustomFieldService();

        int expectedMaxResults = 5;
        CustomFieldPageResponseModel customFields = customFieldService.getCustomFields(0, expectedMaxResults, null);
        assertEquals(expectedMaxResults, customFields.getMaxResults());
    }

}
