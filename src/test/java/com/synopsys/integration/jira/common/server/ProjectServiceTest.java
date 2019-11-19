package com.synopsys.integration.jira.common.server;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jira.common.exception.JiraPreconditionNotMetException;
import com.synopsys.integration.jira.common.model.components.ProjectComponent;
import com.synopsys.integration.jira.common.model.response.IssueResponseModel;
import com.synopsys.integration.jira.common.model.response.IssueTypeResponseModel;
import com.synopsys.integration.jira.common.model.response.UserDetailsResponseModel;
import com.synopsys.integration.jira.common.rest.service.IssuePropertyService;
import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;
import com.synopsys.integration.jira.common.server.service.IssueService;
import com.synopsys.integration.jira.common.server.service.JiraServerServiceFactory;
import com.synopsys.integration.jira.common.server.service.ProjectService;
import com.synopsys.integration.rest.exception.IntegrationRestException;

public class ProjectServiceTest extends JiraServerServiceTest {
    @Test
    public void getProjectsTest() throws IntegrationException {
        validateConfiguration();
        JiraServerServiceFactory serviceFactory = createServiceFactory();

        ProjectService projectService = serviceFactory.createProjectService();

        List<ProjectComponent> projects = projectService.getProjects();
        assertFalse(projects.isEmpty());
    }

    @Ignore
    @Test
    public void atlassianTest() throws IntegrationException {
        validateConfiguration();
        JiraServerServiceFactory serviceFactory = createServiceFactory();

        ProjectService projectService = serviceFactory.createProjectService();
        ProjectComponent atlassianTestProject = projectService.getProjects().stream().filter(project -> project.getName().equals("AtlassianTestProject")).findAny()
                                                    .orElseThrow(() -> new IntegrationException("Could not find the Jira project."));

        IssueTypeResponseModel foundIssueType = serviceFactory.createIssueTypeService().getAllIssueTypes().stream()
                                                    .filter(issueType -> issueType.getName().equalsIgnoreCase("Task"))
                                                    .findFirst()
                                                    .orElseThrow(() -> new JiraPreconditionNotMetException("Issue type not found; issue type Task"));
        UserDetailsResponseModel foundUserDetails = serviceFactory.createUserSearchService().findUserByUsername("admin")
                                                        .orElseThrow(() -> new JiraPreconditionNotMetException("Reporter user with email not found; email: admin"));

        IssueService issueService = serviceFactory.createIssueService();
        IssuePropertyService issuePropertyService = serviceFactory.createIssuePropertyService();

        List<Integer> errorIndexes = new ArrayList<>();
        try {
            String issueTypeId = foundIssueType.getId();
            String userName = foundUserDetails.getName();
            String projectId = atlassianTestProject.getId();

            int offset = 950000;
            while (offset > 0) {
                List<Integer> range = IntStream.rangeClosed(offset - 999, offset).boxed().collect(Collectors.toList());
                runParallel(range, issueService, issuePropertyService, issueTypeId, userName, projectId, errorIndexes);
                serviceFactory = createServiceFactory();
                issueService = serviceFactory.createIssueService();
                issuePropertyService = serviceFactory.createIssuePropertyService();
                offset = offset - 1000;
            }
        } finally {
            String couldNotCreateIndexes = StringUtils.join(errorIndexes, ", ");
            System.err.println("Could not create these " + couldNotCreateIndexes);
        }

    }

    private void runParallel(List<Integer> range, IssueService issueService, IssuePropertyService issuePropertyService, String issueTypeId, String userName, String projectId, List<Integer> errorIndexes) {
        range.stream().parallel().forEach(index -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //ignore
            }
            IssueResponseModel issue = createIssue(issueService, issueTypeId, userName, projectId, index);
            if (issue == null) {
                errorIndexes.add(index);
            } else {
                if (addProperties(issuePropertyService, issue.getKey(), index)) {
                    System.out.println("Successfully created issue #" + index);
                } else {
                    errorIndexes.add(index);
                }
            }
        });
    }

    private IssueResponseModel createIssue(IssueService issueService, String issueTypeId, String reporterUsername, String projectID, int index) {

        IssueRequestModelFieldsBuilder issueRequestModelFieldsBuilder = new IssueRequestModelFieldsBuilder();
        issueRequestModelFieldsBuilder.setSummary(index + " atlassian test issue.");
        issueRequestModelFieldsBuilder.setDescription("Test description");

        try {
            IssueResponseModel issue = issueService.createIssue(issueTypeId, reporterUsername, projectID, issueRequestModelFieldsBuilder);
            return issue;
        } catch (IntegrationRestException e) {
            System.err.println("Failed to created issue #" + index + ". Reason:" + e.getHttpResponseContent());
            e.printStackTrace();
        } catch (IntegrationException e) {
            System.err.println("Failed to created issue #" + index);
            e.printStackTrace();
        }
        return null;
    }

    private Boolean addProperties(IssuePropertyService issuePropertyService, String issueKey, int index) {
        try {
            AlertJiraIssueProperties properties = new AlertJiraIssueProperties("JR Test Provider", "Topic #", String.valueOf(index), "subTopicName", "subTopicValue",
                "category", "componentName", "componentValue", "subComponentName", "subComponentValue", "additionalKey");
            issuePropertyService.setProperty(issueKey, "com-synopsys-integration-alert", properties);
            return true;
        } catch (IntegrationRestException e) {
            System.err.println("Failed to created issue #" + index + ". Reason:" + e.getHttpResponseContent());
            e.printStackTrace();
        } catch (IntegrationException e) {
            System.err.println("Failed to created issue #" + index);
            e.printStackTrace();
        }
        return false;
    }
}
