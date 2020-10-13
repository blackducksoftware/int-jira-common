package com.synopsys.integration.jira.common.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTestIT;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTestUtility;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;

public class IssueTypeServiceTestIT extends JiraCloudParameterizedTestIT {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void getAllIssueTypesTest(JiraHttpClient jiraHttpClient) throws IntegrationException {
        JiraCloudServiceTestUtility.validateConfiguration();

        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueTypeService issueTypeService = serviceFactory.createIssueTypeService();

        List<IssueTypeResponseModel> allIssueTypes = issueTypeService.getAllIssueTypes();
        assertFalse(allIssueTypes.isEmpty());
    }

}
