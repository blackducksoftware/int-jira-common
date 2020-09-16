package com.synopsys.integration.jira.common.cloud.service;

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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.JiraTestConstants;
import com.synopsys.integration.jira.common.cloud.JiraCloudParameterizedTest;
import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.cloud.model.IssueCreationRequestModel;
import com.synopsys.integration.jira.common.exception.JiraPreconditionNotMetException;
import com.synopsys.integration.jira.common.model.EntityProperty;
import com.synopsys.integration.jira.common.model.components.FieldUpdateOperationComponent;
import com.synopsys.integration.jira.common.model.components.IdComponent;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.components.StatusDetailsComponent;
import com.synopsys.integration.jira.common.model.components.TransitionComponent;
import com.synopsys.integration.jira.common.model.request.IssueCommentRequestModel;
import com.synopsys.integration.jira.common.model.request.IssueRequestModel;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.PageOfProjectsResponseModel;
import com.synopsys.integration.jira.common.model.response.TransitionsResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.JiraHttpClient;

public class IssueServiceTest extends JiraCloudParameterizedTest {

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testCreateIssue(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssue.getId());
        assertEquals(createdIssue.getKey(), foundIssue.getKey());
        assertTrue(foundIssue.getProperties().containsKey(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY));
        assertNotNull(foundIssue.getProperties().get(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY));
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testCreateNullFieldExceptions(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        ProjectService projectService = serviceFactory.createProjectService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: ");
        fieldsBuilder.setSummary("Test Issue ");
        List<EntityProperty> properties = new LinkedList<>();
        PageOfProjectsResponseModel projects = projectService.getProjects();
        ProjectComponent validProject = projects.getProjects().stream()
                                            .filter(currentProject -> currentProject.getName().equals(JiraCloudServiceTestUtility.getTestProject()))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UserDetailsResponseModel validUserDetails = userSearchService.findUser(JiraCloudServiceTestUtility.getEnvUserEmail()).stream()
                                                        .findFirst()
                                                        .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        String validIssueType = "bug";
        String validEmailAddress = validUserDetails.getEmailAddress();
        String validProjectName = validProject.getName();

        IssueCreationRequestModel emailRequestModel = new IssueCreationRequestModel(null, validIssueType, validProjectName, fieldsBuilder, properties);
        JiraPreconditionNotMetException emailException = assertThrows(JiraPreconditionNotMetException.class, () -> issueService.createIssue(emailRequestModel));
        IssueCreationRequestModel issueTypeRequestModel = new IssueCreationRequestModel(validEmailAddress, null, validProjectName, fieldsBuilder, properties);
        JiraPreconditionNotMetException issueTypeException = assertThrows(JiraPreconditionNotMetException.class, () -> issueService.createIssue(issueTypeRequestModel));
        IssueCreationRequestModel projectRequestModel = new IssueCreationRequestModel(validEmailAddress, validIssueType, null, fieldsBuilder, properties);
        JiraPreconditionNotMetException projectException = assertThrows(JiraPreconditionNotMetException.class, () -> issueService.createIssue(projectRequestModel));

        assertTrue(emailException.getMessage().contains("Reporter user with email not found; email:"));
        assertTrue(issueTypeException.getMessage().contains("Issue type not found; issue type"));
        assertTrue(projectException.getMessage().contains("Project not found; project name:"));
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testCreateFieldsNotFoundExceptions(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        ProjectService projectService = serviceFactory.createProjectService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: ");
        fieldsBuilder.setSummary("Test Issue ");
        List<EntityProperty> properties = new LinkedList<>();

        PageOfProjectsResponseModel projects = projectService.getProjects();
        ProjectComponent validProject = projects.getProjects().stream()
                                            .filter(currentProject -> currentProject.getName().equals(JiraCloudServiceTestUtility.getTestProject()))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UserDetailsResponseModel validUserDetails = userSearchService.findUser(JiraCloudServiceTestUtility.getEnvUserEmail()).stream()
                                                        .findFirst()
                                                        .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        String validIssueType = "bug";
        String validEmailAddress = validUserDetails.getEmailAddress();
        String validProjectName = validProject.getName();

        IssueCreationRequestModel emailRequestModel = new IssueCreationRequestModel("unknown_user_123_abcd@a.unknown.test.domain.com", validIssueType, validProjectName, fieldsBuilder, properties);
        JiraPreconditionNotMetException emailException = assertThrows(JiraPreconditionNotMetException.class, () -> issueService.createIssue(emailRequestModel));
        IssueCreationRequestModel issueTypeRequestModel = new IssueCreationRequestModel(validEmailAddress, "unknown_test_issue_type", validProjectName, fieldsBuilder, properties);
        JiraPreconditionNotMetException issueTypeException = assertThrows(JiraPreconditionNotMetException.class, () -> issueService.createIssue(issueTypeRequestModel));
        IssueCreationRequestModel projectRequestModel = new IssueCreationRequestModel(validEmailAddress, validIssueType, "unknown_project_name", fieldsBuilder, properties);
        JiraPreconditionNotMetException projectException = assertThrows(JiraPreconditionNotMetException.class, () -> issueService.createIssue(projectRequestModel));

        assertTrue(emailException.getMessage().contains("Reporter user with email not found; email:"));
        assertTrue(issueTypeException.getMessage().contains("Issue type not found; issue type"));
        assertTrue(projectException.getMessage().contains("Project not found; project name:"));

    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testUpdateIssue(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        UserDetailsResponseModel userDetails = userSearchService.findUser(JiraCloudServiceTestUtility.getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());
        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setAssigneeId(userDetails.getAccountId());
        Map<String, List<FieldUpdateOperationComponent>> update = new HashMap<>();

        String propertyValue = UUID.randomUUID().toString();
        List<EntityProperty> properties = new LinkedList<>();
        properties.add(new EntityProperty(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY, propertyValue));
        IssueRequestModel requestModel = new IssueRequestModel(foundIssue.getId(), null, fieldsBuilder, update, properties);
        issueService.updateIssue(requestModel);
        IssueResponseModel foundIssueWithAssignee = issueService.getIssue(createdIssue.getId());

        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssueWithAssignee.getId());
        assertNull(foundIssue.getFields().getAssignee());
        assertNotNull(foundIssueWithAssignee.getFields().getAssignee());
        assertEquals(userDetails.getAccountId(), foundIssueWithAssignee.getFields().getAssignee().getAccountId());
        assertTrue(foundIssueWithAssignee.getProperties().containsKey(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY));
        assertEquals(propertyValue, foundIssueWithAssignee.getProperties().get(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY));
        assertEquals(propertyValue, foundIssueWithAssignee.getProperties().get(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY));
        assertNotEquals(foundIssue.getProperties().get(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY), foundIssueWithAssignee.getProperties().get(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY));
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testAddComment(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
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

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testGetStatus(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();

        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());

        StatusDetailsComponent status = issueService.getStatus(foundIssue.getId());
        // delete the issue
        issueService.deleteIssue(createdIssue.getId());

        assertEquals(createdIssue.getId(), foundIssue.getId());
        assertEquals("Open", status.getName());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void testTransitionIssue(JiraHttpClient jiraHttpClient) throws Exception {
        JiraCloudServiceTestUtility.validateConfiguration();
        JiraCloudServiceFactory serviceFactory = JiraCloudServiceTestUtility.createServiceFactory(jiraHttpClient);
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();

        UserDetailsResponseModel userDetails = userSearchService.findUser(JiraCloudServiceTestUtility.getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));

        // create an issue
        IssueResponseModel createdIssue = createIssue(serviceFactory);
        IssueResponseModel foundIssue = issueService.getIssue(createdIssue.getId());

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setAssigneeId(userDetails.getAccountId());

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

    private IssueResponseModel createIssue(JiraCloudServiceFactory serviceFactory) throws IntegrationException {
        IssueService issueService = serviceFactory.createIssueService();
        UserSearchService userSearchService = serviceFactory.createUserSearchService();
        ProjectService projectService = serviceFactory.createProjectService();
        PageOfProjectsResponseModel projects = projectService.getProjects();
        UserDetailsResponseModel userDetails = userSearchService.findUser(JiraCloudServiceTestUtility.getEnvUserEmail()).stream()
                                                   .findFirst()
                                                   .orElseThrow(() -> new IllegalStateException("Jira User not found"));
        ProjectComponent project = projects.getProjects().stream()
                                       .filter(currentProject -> currentProject.getName().equals(JiraCloudServiceTestUtility.getTestProject()))
                                       .findFirst()
                                       .orElseThrow(() -> new IllegalStateException("Jira Projects not found"));
        UUID uniqueId = UUID.randomUUID();

        IssueRequestModelFieldsBuilder fieldsBuilder = new IssueRequestModelFieldsBuilder();
        fieldsBuilder.setDescription("Description of the test issue: " + uniqueId.toString());
        fieldsBuilder.setSummary("Test Issue " + uniqueId.toString());

        String propertyValue = UUID.randomUUID().toString();
        List<EntityProperty> properties = new LinkedList<>();
        properties.add(new EntityProperty(JiraTestConstants.CLOUD_TEST_PROPERTY_KEY, propertyValue));
        IssueCreationRequestModel requestModel = new IssueCreationRequestModel(userDetails.getEmailAddress(), "bug", project.getName(), fieldsBuilder, properties);

        // create an issue
        return issueService.createIssue(requestModel);
    }

}
