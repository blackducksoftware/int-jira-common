package com.blackduck.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.blackduck.integration.jira.common.model.response.CustomFieldCreationResponseModel;
import com.blackduck.integration.jira.common.rest.JiraHttpClient;

public class FieldServiceTestIT extends JiraCloudParameterizedTestIT {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getFieldsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        FieldService fieldService = serviceFactory.createFieldService();

        List<CustomFieldCreationResponseModel> userVisibleFields = fieldService.getUserVisibleFields();
        assertTrue(userVisibleFields.size() > 0);
    }
}
