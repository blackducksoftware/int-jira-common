package com.synopsys.integration.jira.common.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.cloud.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.cloud.model.IssueSearchResponseModel;
import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;

public class IssueSearchServiceTest extends JiraCloudServiceTest {

    @Test
    public void findIssuesByDescriptionTest() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(getTestProject()))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));

        UUID uniqueId = UUID.randomUUID();
        String uniqueIdString = uniqueId.toString();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueIdString);
        fieldsBuilder.setSummary("Test Issue " + uniqueIdString);

        String issueType = "bug";
        List<EntityProperty> properties = new LinkedList<>();
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        IssueSearchResponseModel foundIssues = issueSearchService.findIssuesByDescription(project.getKey(), issueType, uniqueIdString);

        assertEquals(1, foundIssues.getIssues().size());
        IssueResponseModel foundIssue = foundIssues.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(createdIssue.getId(), foundIssue.getId());
    }

    @Test
    public void testSearchForIssueWithSingleComment() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(getTestProject()))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UUID uniqueId = UUID.randomUUID();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueId.toString());
        fieldsBuilder.setSummary("Test Issue " + uniqueId.toString());

        String issueType = "bug";
        String commentValue = "synopsys_generated_id: " + UUID.randomUUID().toString();
        List<EntityProperty> properties = new LinkedList<>();
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);

        // create an issue
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);
        IssueCommentRequestModel commentRequestModel = new IssueCommentRequestModel(createdIssue.getId(), commentValue, null, null, null);
        issueService.addComment(commentRequestModel);

        IssueResponseModel issueFromIssueService = issueService.getIssue(createdIssue.getId());

        IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssuesByComment(project.getKey(), issueType, commentValue);
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(1, issueFromSearchService.getIssues().size());
        IssueResponseModel foundIssue = issueFromSearchService.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(issueFromIssueService.getId(), foundIssue.getId());
    }

    @Test
    public void testSearchForIssueWithMultipleComments() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(getTestProject()))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UUID uniqueId = UUID.randomUUID();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueId.toString());
        fieldsBuilder.setSummary("Test Issue " + uniqueId.toString());

        String issueType = "bug";
        String commentValue = "synopsys_generated_id: " + UUID.randomUUID().toString();
        List<EntityProperty> properties = new LinkedList<>();
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);

        // create an issue
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        IssueCommentRequestModel firstComment = new IssueCommentRequestModel(createdIssue.getId(), "First comment.", null, null, null);
        issueService.addComment(firstComment);
        IssueCommentRequestModel secondComment = new IssueCommentRequestModel(createdIssue.getId(), "sysnopsys_generated_id: bad value", null, null, null);
        issueService.addComment(secondComment);

        IssueCommentRequestModel validComment = new IssueCommentRequestModel(createdIssue.getId(), commentValue, null, null, null);
        issueService.addComment(validComment);

        IssueCommentRequestModel lastComment = new IssueCommentRequestModel(createdIssue.getId(), "Last comment", null, null, null);
        issueService.addComment(lastComment);

        IssueResponseModel issueFromIssueService = issueService.getIssue(createdIssue.getId());

        IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssuesByComment(project.getKey(), issueType, commentValue);
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(1, issueFromSearchService.getIssues().size());
        IssueResponseModel foundIssue = issueFromSearchService.getIssues().stream().findFirst().orElseThrow(() -> new IllegalStateException("Issue not found"));
        assertEquals(issueFromIssueService.getId(), foundIssue.getId());
    }

    @Test
    public void testSearchForIssueByCommentNoMatches() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        IssueSearchService issueSearchService = serviceFactory.createIssueSearchService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(getTestProject()))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UUID uniqueId = UUID.randomUUID();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueId.toString());
        fieldsBuilder.setSummary("Test Issue " + uniqueId.toString());

        String issueType = "Task";
        String commentValue = "synopsys_generated_id: " + UUID.randomUUID().toString();
        List<EntityProperty> properties = new LinkedList<>();
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), issueType, project.getName(), fieldsBuilder, properties);

        // create an issue
        IssueResponseModel createdIssue = issueService.createIssue(requestModel);

        IssueCommentRequestModel firstComment = new IssueCommentRequestModel(createdIssue.getId(), "First comment.", null, null, null);
        issueService.addComment(firstComment);
        IssueCommentRequestModel secondComment = new IssueCommentRequestModel(createdIssue.getId(), "sysnopsys_generated_id: bad value", null, null, null);
        issueService.addComment(secondComment);
        IssueCommentRequestModel lastComment = new IssueCommentRequestModel(createdIssue.getId(), "Last comment", null, null, null);
        issueService.addComment(lastComment);

        IssueSearchResponseModel issueFromSearchService = issueSearchService.findIssuesByComment(project.getKey(), issueType, commentValue);
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(0, issueFromSearchService.getIssues().size());
    }
}
