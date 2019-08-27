package com.synopsys.integration.jira.common.cloud.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.cloud.model.components.IdComponent;
import com.synopsys.integration.jira.common.cloud.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.cloud.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.cloud.model.components.TransitionComponent;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.cloud.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.cloud.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.cloud.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.cloud.rest.service.IssueService;
import com.synopsys.integration.jira.common.cloud.rest.service.JiraCloudServiceFactory;
import com.synopsys.integration.jira.common.cloud.rest.service.ProjectService;
import com.synopsys.integration.jira.common.cloud.rest.service.UserSearchService;
import com.synopsys.integration.jira.common.model.EntityProperty;

public class IssueServiceTest extends JiraServiceTest {

    @Test
    public void testCreateIssue() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        final IssueResponseModel createdIssue = createIssue(serviceFactory);
        final IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssue.getId());
        assertEquals(createdIssue.getKey(), foundIssue.getKey());
        assertTrue(foundIssue.getProperties().containsKey(TEST_PROPERTY_KEY));
        assertNotNull(foundIssue.getProperties().get(TEST_PROPERTY_KEY));
    }

    @Test
    public void testCreateNullFieldExceptions() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final ProjectService projectService = serviceFactory.createProjectService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: ");
        fieldsBuilder.setSummary("Test Issue ");
        final List<EntityProperty> properties = new LinkedList<>();
        final PageOfProjectsResponseModel projects = projectService.getProjects();
        final ProjectComponent validProject = projects.getProjects().stream()
                                                  .findFirst()
                                                  .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        final UserDetailsResponseModel validUserDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                              .findFirst()
                                                              .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        final String validIssueType = "bug";
        final String validEmailAddress = validUserDetails.getEmailAddress();
        final String validProjectName = validProject.getName();

        final IssueCreationRequestModel emailRequestModel = new IssueCreationRequestModel(null, validIssueType, validProjectName, fieldsBuilder, properties);
        final IllegalStateException emailException = assertThrows(IllegalStateException.class, () -> issueService.createIssue(emailRequestModel));
        final IssueCreationRequestModel issueTypeRequestModel = new IssueCreationRequestModel(validEmailAddress, null, validProjectName, fieldsBuilder, properties);
        final IllegalStateException issueTypeException = assertThrows(IllegalStateException.class, () -> issueService.createIssue(issueTypeRequestModel));
        final IssueCreationRequestModel projectRequestModel = new IssueCreationRequestModel(validEmailAddress, validIssueType, null, fieldsBuilder, properties);
        final IllegalStateException projectException = assertThrows(IllegalStateException.class, () -> issueService.createIssue(projectRequestModel));

        assertTrue(emailException.getMessage().contains("Reporter user with email not found; email:"));
        assertTrue(issueTypeException.getMessage().contains("Issue type not found; issue type"));
        assertTrue(projectException.getMessage().contains("Project not found; project name:"));
    }

    @Test
    public void testCreateFieldsNotFoundExceptions() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final ProjectService projectService = serviceFactory.createProjectService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: ");
        fieldsBuilder.setSummary("Test Issue ");
        final List<EntityProperty> properties = new LinkedList<>();

        final PageOfProjectsResponseModel projects = projectService.getProjects();
        final ProjectComponent validProject = projects.getProjects().stream()
                                                  .findFirst()
                                                  .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        final UserDetailsResponseModel validUserDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                              .findFirst()
                                                              .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        final String validIssueType = "bug";
        final String validEmailAddress = validUserDetails.getEmailAddress();
        final String validProjectName = validProject.getName();

        final IssueCreationRequestModel emailRequestModel = new IssueCreationRequestModel("unknown_user_123_abcd@a.unknown.test.domain.com", validIssueType, validProjectName, fieldsBuilder, properties);
        final IllegalStateException emailException = assertThrows(IllegalStateException.class, () -> issueService.createIssue(emailRequestModel));
        final IssueCreationRequestModel issueTypeRequestModel = new IssueCreationRequestModel(validEmailAddress, "unknown_test_issue_type", validProjectName, fieldsBuilder, properties);
        final IllegalStateException issueTypeException = assertThrows(IllegalStateException.class, () -> issueService.createIssue(issueTypeRequestModel));
        final IssueCreationRequestModel projectRequestModel = new IssueCreationRequestModel(validEmailAddress, validIssueType, "unknown_project_name", fieldsBuilder, properties);
        final IllegalStateException projectException = assertThrows(IllegalStateException.class, () -> issueService.createIssue(projectRequestModel));

        assertTrue(emailException.getMessage().contains("Reporter user with email not found; email:"));
        assertTrue(issueTypeException.getMessage().contains("Issue type not found; issue type"));
        assertTrue(projectException.getMessage().contains("Project not found; project name:"));

    }

    @Test
    public void testUpdateIssue() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();

        final UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                         .findFirst()
                                                         .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        // create an issue
        final IssueResponseModel createdIssue = createIssue(serviceFactory);
        final IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setAssignee(userDetails.getAccountId());
        final Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();

        final String propertyValue = UUID.randomUUID().toString();
        final List<EntityProperty> properties = new LinkedList<>();
        properties.add(new EntityProperty(TEST_PROPERTY_KEY, propertyValue));
        final IssueRequestModel requestModel = new IssueRequestModel(foundIssue.getId(), null, fieldsBuilder, update, properties);
        issueService.updateIssue(requestModel);
        final IssueResponseModel foundIssueWithAssignee = issueService.getIssue(createdIssue.getId());

        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssueWithAssignee.getId());
        assertNull(foundIssue.getFields().getAssignee());
        assertNotNull(foundIssueWithAssignee.getFields().getAssignee());
        assertEquals(userDetails.getAccountId(), foundIssueWithAssignee.getFields().getAssignee().getAccountId());
        assertTrue(foundIssueWithAssignee.getProperties().containsKey(TEST_PROPERTY_KEY));
        assertEquals(propertyValue, foundIssueWithAssignee.getProperties().get(TEST_PROPERTY_KEY));
        assertEquals(propertyValue, foundIssueWithAssignee.getProperties().get(TEST_PROPERTY_KEY));
        assertNotEquals(foundIssue.getProperties().get(TEST_PROPERTY_KEY), foundIssueWithAssignee.getProperties().get(TEST_PROPERTY_KEY));
    }

    @Test
    public void testAddComment() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        final IssueResponseModel createdIssue = createIssue(serviceFactory);
        final IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        final UUID uniqueId = UUID.randomUUID();
        final IssueCommentRequestModel issueCommentModel = new IssueCommentRequestModel(foundIssue.getId(), uniqueId.toString(), null, true, null);

        issueService.addComment(issueCommentModel);

        final IssueResponseModel foundIssueWithComments = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssueWithComments.getId());
        assertEquals(1, foundIssueWithComments.getFields().getComment().getTotal().intValue());
        assertEquals(uniqueId.toString(), foundIssueWithComments.getFields().getComment().getComments().get(0).getBody());
    }

    @Test
    public void testGetStatus() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        final IssueResponseModel createdIssue = createIssue(serviceFactory);
        final IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());

        final StatusDetailsComponent status = issueService.getStatus(foundIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssue.getId());
        assertEquals("Open", status.getName());
    }

    @Test
    public void testTransitionIssue() throws Exception {
        validateConfiguration();
        final JiraCloudServiceFactory serviceFactory = createServiceFactory();
        final IssueService issueService = serviceFactory.createIssueService();
        final UserSearchService userSearchService = serviceFactory.createUserSearchService();

        final UserDetailsResponseModel userDetails = userSearchService.findUser(getEnvUserEmail()).stream()
                                                         .findFirst()
                                                         .orElseThrow(() -> new IllegalStateException("Jira User not found"));

        // create an issue
        final IssueResponseModel createdIssue = createIssue(serviceFactory);
        final IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());

        final IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setAssignee(userDetails.getAccountId());

        final Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();
        final List<EntityProperty> properties = new LinkedList<>();

        final TransitionsResponseModel transitionsResponseModel = issueService.getTransitions(foundIssue.getId());
        final TransitionComponent resolveTransition = transitionsResponseModel.findFirstTransitionByName("Resolve Issue")
                                                          .orElseThrow(() -> new IllegalStateException("Transition not found for issue"));

        final IdComponent transitionId = new IdComponent(resolveTransition.getId());
        final IssueRequestModel transitionRequest = new IssueRequestModel(foundIssue.getId(), transitionId, fieldsBuilder, update, properties);
        issueService.transitionIssue(transitionRequest);

        final IssueResponseModel foundIssueWithTransition = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssueWithTransition.getId());
        assertNotEquals(foundIssue.getFields().getUpdated(), foundIssueWithTransition.getFields().getUpdated());
    }

    private IssueResponseModel createIssue(final JiraCloudServiceFactory serviceFactory) throws IntegrationException {
        final IssueService issueService = serviceFactory.createIssueService();
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
        return issueService.createIssue(requestModel);
    }
}
