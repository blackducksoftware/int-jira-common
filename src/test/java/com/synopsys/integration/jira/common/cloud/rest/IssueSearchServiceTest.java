package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.components.IssueComponent;
import com.synopsys.integration.jira.common.cloud.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueSearchResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.cloud.rest.service.IssueSearchService;
import com.synopsys.integration.jira.common.cloud.rest.service.IssueService;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.rest.service.ProjectService;
import com.synopsys.integration.jira.common.cloud.rest.service.UserSearchService;
import com.synopsys.integration.jira.common.model.EntityProperty;

public class IssueSearchServiceTest extends JiraServiceTest {

    @Test
    public void testSearchForIssueByProperty() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();
        final ProjectService projectService = serviceFactory.createProjectService();

        final PageOfProjectsResponseModel projects = projectService.getProjects();
        final UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                         .findFirst()
                                                         .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        final ProjectComponent project = projects.getProjects().stream()
                                             .findFirst()
                                             .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        final UUID uniqueId = UUID.randomUUID();

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueId.toString());
        fieldsBuilder.setSummary("Test Issue " + uniqueId.toString());

        final String propertyValue = UUID.randomUUID().toString();
        final List<EntityProperty> properties = new LinkedList<>();
        properties.add(new EntityProperty(TEST_PROPERTY_KEY, propertyValue));
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), "bug", project.getName(), fieldsBuilder, properties);

        // create an issue
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);
        IssueResponseModel issueFromIssueService = issueService.getIssue(createdIssue.getId());

        IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssueByProperty(TEST_PROPERTY_KEY, propertyValue);
        //issueService.deleteIssue(createdIssue.getId());

        assertEquals(1, issueFromSearchService.getIssues().size());
        IssueComponent foundIssue = issueFromSearchService.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(issueFromIssueService.getId(), foundIssue.getId());
        assertEquals(issueFromIssueService.getProperties().get(TEST_PROPERTY_KEY), foundIssue.getProperties().get(TEST_PROPERTY_KEY));

    }
}
