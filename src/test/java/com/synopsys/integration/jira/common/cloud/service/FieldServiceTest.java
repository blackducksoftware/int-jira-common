package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.model.response.CustomFieldCreationResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;

public class FieldServiceTest extends JiraCloudParameterizedTest {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getFieldsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        FieldService fieldService = serviceFactory.createFieldService();

        List<CustomFieldCreationResponseModel> userVisibleFields = fieldService.getUserVisibleFields();
        assertTrue(userVisibleFields.size() > 0);
    }
}
