package com.synopsys.integration.jira.common.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.service.JiraCloudServiceTest;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssueTypeService;

public class IssueTypeServiceTest extends JiraCloudServiceTest {
    @Test
    public void getAllIssueTypesTest() throws IntegrationException {
        validateConfiguration();

        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueTypeService issueTypeService = serviceFactory.createIssueTypeService();

        List<IssueTypeResponseModel> allIssueTypes = issueTypeService.getAllIssueTypes();
        assertFalse(allIssueTypes.isEmpty());
    }

}
