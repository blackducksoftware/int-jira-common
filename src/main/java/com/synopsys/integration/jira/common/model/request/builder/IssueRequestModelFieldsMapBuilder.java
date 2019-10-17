package com.synopsys.integration.jira.common.model.request.builder;

import java.util.Map;

public interface IssueRequestModelFieldsMapBuilder {
    Map<String, Object> build();

    IssueRequestModelFieldsMapBuilder copyFields(IssueRequestModelFieldsMapBuilder original);

}
