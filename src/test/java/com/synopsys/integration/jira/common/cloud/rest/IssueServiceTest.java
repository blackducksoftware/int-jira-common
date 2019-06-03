package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.cloud.model.IdComponent;
import com.synopsys.integration.jira.common.cloud.model.ProjectComponent;
import com.synopsys.integration.jira.common.cloud.model.TransitionComponent;
import com.synopsys.integration.jira.common.cloud.model.UserDetailsComponent;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.cloud.rest.service.IssueService;
import com.synopsys.integration.jira.common.cloud.rest.service.IssueTypeService;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.rest.service.ProjectService;
import com.synopsys.integration.jira.common.cloud.rest.service.UserSearchService;
import com.synopsys.integration.jira.common.model.EntityProperty;

public class IssueServiceTest extends JiraServiceTest {
    @Test
    public void testCreateIssue() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssue.getId());
        assertEquals(createdIssue.getKey(), foundIssue.getKey());
    }

    @Test
    public void testAddComment() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        UUID uniqueId = UUID.randomUUID();
        IssueCommentRequestModel issueCommentModel = new IssueCommentRequestModel(foundIssue.getId(), uniqueId.toString(), null, true, null);

        issueService.addComment(issueCommentModel);

        IssueResponseModel foundIssueWithComments = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssueWithComments.getId());
        assertEquals(1, foundIssueWithComments.getFields().getComment().getTotal().intValue());
        assertEquals(uniqueId.toString(), foundIssueWithComments.getFields().getComment().getComments().get(0).getBody());
    }

    @Test
    public void testTransitionIssue() throws Exception {
        validateConfiguration();
        JiraCloudServiceFactory serviceFactory = createServiceFactory();
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        UserDetailsComponent userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                               .findFirst()
                                               .orElseThrow(() -> new IllegalStateException("Jira User not found"));

        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setAssignee(userDetails.getAccountId());

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        List<EntityProperty> properties = new LinkedList<>();

        TransitionsResponseModel transitionsResponseModel = issueService.getTransitions(foundIssue.getId());
        TransitionComponent resolveTransition = transitionsResponseModel.findFirstTransitionByName("Resolve Issue")
                                                    .orElseThrow(() -> new IllegalStateException("Transition not found for issue"));

        IdComponent transitionId = new IdComponent(resolveTransition.getId());
        IssueRequestModel transitionRequest = new IssueRequestModel(foundIssue.getId(), transitionId, fieldsBuilder, update, properties);
        issueService.transitionIssue(transitionRequest);

        IssueResponseModel foundIssueWithTransition = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssueWithTransition.getId());
        assertNotEquals(foundIssue.getFields().getUpdated(), foundIssueWithTransition.getFields().getUpdated());
    }

    private IssueResponseModel createIssue(final JiraCloudServiceFactory serviceFactory) throws IntegrationException {
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();
        IssueTypeService issueTypeService = serviceFactory.createIssueTypeService();

        IssueTypeResponseModel bugIssueType = issueTypeService.getAllIssueTypes().stream()
                                                  .filter(issueType -> "bug".equalsIgnoreCase(issueType.getName()))
                                                  .findFirst()
                                                  .orElseThrow(() -> new IllegalStateException("Jira Bug issue type not found."));
        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsComponent userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                               .findFirst()
                                               .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UUID uniqueId = UUID.randomUUID();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setReporter(userDetails.getAccountId());
        fieldsBuilder.setProject(project.getId());
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueId.toString());
        fieldsBuilder.setSummary("Test Issue " + uniqueId.toString());
        fieldsBuilder.setIssueType(bugIssueType.getId());

        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        List<EntityProperty> properties = new LinkedList<>();
        IssueRequestModel requestModel = new IssueRequestModel(fieldsBuilder, update, properties);

        // create an issue
        return issueService.createIssue(requestModel);
    }
}
