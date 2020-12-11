package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.TestAbortedException;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.model.response.CustomFieldCreationResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;
import com.synopsys.integration.jira.common.server.service.FieldService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;

public class FieldServiceTestIT extends JiraServerParameterizedTestIT {
    @ParameterizedTest
    @MethodSource("getParameters")
    public void getUserVisibleFieldsTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraServerServiceTestUtility.validateConfiguration();

        JiraServerServiceFactory serviceFactory = JiraServerServiceTestUtility.createServiceFactory(jiraHttpClient);
        FieldService fieldService = serviceFactory.createFieldService();

        List<CustomFieldCreationResponseModel> userVisibleFields = fieldService.getUserVisibleFields();
        if (!userVisibleFields.isEmpty()) {
            CustomFieldCreationResponseModel responseModel = userVisibleFields
                                                                 .stream()
                                                                 .findAny()
                                                                 .orElseThrow(TestAbortedException::new);
            assertNotNull(responseModel.getId(), "Expected id not to be null");
            assertNotNull(responseModel.getName(), "Expected name not to be null");
        } else {
            throw new TestAbortedException("No user-visible feilds to test");
        }
    }

}
