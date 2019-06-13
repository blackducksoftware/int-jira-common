package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.components.IssueComponent;
import com.synopsys.integration.jira.common.cloud.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCommentRequestModel;
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
    public void findIssuesByDescriptionTest() throws Exception {
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
        final String uniqueIdString = uniqueId.toString();

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueIdString);
        fieldsBuilder.setSummary("Test Issue " + uniqueIdString);

        final String issueType = "bug";
        final List<EntityProperty> properties = new LinkedList<>();
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);
        final IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        final IssueSearchResponseModel foundIssues = issueSearchService.findIssuesByDescription(project.getKey(), issueType, uniqueIdString);

        assertEquals(1, foundIssues.getIssues().size());
        IssueComponent foundIssue = foundIssues.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(createdIssue.getId(), foundIssue.getId());
    }

    @Test
    public void testSearchForIssueWithSingleComment() throws Exception {
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

        final String issueType = "bug";
        final String commentValue = "synopsys_generated_id: " + UUID.randomUUID().toString();
        final List<EntityProperty> properties = new LinkedList<>();
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);

        // create an issue
        final IssueResponseModel createdIssue = issueService.createIssue(requestModel);
        final IssueCommentRequestModel commentRequestModel = new IssueCommentRequestModel(createdIssue.getId(), commentValue, null, null, null);
        issueService.addComment(commentRequestModel);

        final IssueResponseModel issueFromIssueService = issueService.getIssue(createdIssue.getId());

        final IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssuesByComment(project.getKey(), issueType, commentValue);
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(1, issueFromSearchService.getIssues().size());
        IssueComponent foundIssue = issueFromSearchService.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(issueFromIssueService.getId(), foundIssue.getId());
    }

    @Test
    public void testSearchForIssueWithMultipleComments() throws Exception {
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

        final String issueType = "bug";
        final String commentValue = "synopsys_generated_id: " + UUID.randomUUID().toString();
        final List<EntityProperty> properties = new LinkedList<>();
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);

        // create an issue
        final IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        final IssueCommentRequestModel firstComment = new IssueCommentRequestModel(createdIssue.getId(), "First comment.", null, null, null);
        issueService.addComment(firstComment);
        final IssueCommentRequestModel secondComment = new IssueCommentRequestModel(createdIssue.getId(), "sysnopsys_generated_id: bad value", null, null, null);
        issueService.addComment(secondComment);

        final IssueCommentRequestModel validComment = new IssueCommentRequestModel(createdIssue.getId(), commentValue, null, null, null);
        issueService.addComment(validComment);

        final IssueCommentRequestModel lastComment = new IssueCommentRequestModel(createdIssue.getId(), "Last comment", null, null, null);
        issueService.addComment(lastComment);

        final IssueResponseModel issueFromIssueService = issueService.getIssue(createdIssue.getId());

        final IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssuesByComment(project.getKey(), issueType, commentValue);
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(1, issueFromSearchService.getIssues().size());
        IssueComponent foundIssue = issueFromSearchService.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(issueFromIssueService.getId(), foundIssue.getId());
    }

    @Test
    public void testSearchForIssueByCommentNoMatches() throws Exception {
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

        final String issueType = "bug";
        final String commentValue = "synopsys_generated_id: " + UUID.randomUUID().toString();
        final List<EntityProperty> properties = new LinkedList<>();
        final IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);

        // create an issue
        final IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        final IssueCommentRequestModel firstComment = new IssueCommentRequestModel(createdIssue.getId(), "First comment.", null, null, null);
        issueService.addComment(firstComment);
        final IssueCommentRequestModel secondComment = new IssueCommentRequestModel(createdIssue.getId(), "sysnopsys_generated_id: bad value", null, null, null);
        issueService.addComment(secondComment);
        final IssueCommentRequestModel lastComment = new IssueCommentRequestModel(createdIssue.getId(), "Last comment", null, null, null);
        issueService.addComment(lastComment);

        final IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssuesByComment(project.getKey(), issueType, commentValue);
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(0, issueFromSearchService.getIssues().size());
    }
}
