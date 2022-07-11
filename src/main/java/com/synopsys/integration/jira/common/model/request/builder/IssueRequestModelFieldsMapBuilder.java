/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.request.builder;

import java.util.Map;

public interface IssueRequestModelFieldsMapBuilder {
    Map<String, Object> build();

    IssueRequestModelFieldsMapBuilder copyFields(IssueRequestModelFieldsMapBuilder original);

}
