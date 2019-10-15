package com.synopsys.integration.jira.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.server.builder.IssueRequestModelFieldsBuilder;

public class PlaceholderTest {
    @Test
    public void test() {
        IssueRequestModelFieldsBuilder builder = new IssueRequestModelFieldsBuilder();
        Map<String, Object> issueRequestModelFields = builder.build();
        assertTrue(issueRequestModelFields.isEmpty(), "Expected the map to be empty");
    }

}
