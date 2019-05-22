package com.synopsys.integration.jira.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.jira.common.cloud.builder.IssueRequestModelFieldsBuilder;

public class PlaceholderTest {
    @Test
    public void test() {
        final IssueRequestModelFieldsBuilder builder = new IssueRequestModelFieldsBuilder();
        final Map<String, Object> issueRequestModelFields = builder.build();
        assertTrue(issueRequestModelFields.isEmpty(), "Expected the map to be empty");
    }

}
