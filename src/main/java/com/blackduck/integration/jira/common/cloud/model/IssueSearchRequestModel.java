/*
 * int-jira-common
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.blackduck.integration.jira.common.enumeration.ExpandableTypes;
import com.blackduck.integration.jira.common.enumeration.QueryValidationStrategy;
import com.blackduck.integration.jira.common.model.request.JiraRequestModel;

public class IssueSearchRequestModel extends JiraRequestModel {
    public static final String ALL_FIELDS_OPTION = "*all";
    public static final List<String> ALL_FIELDS_LIST = Collections.singletonList(ALL_FIELDS_OPTION);
    public static final String NAVIGABLE_FIELDS_OPTION = "*navigable";
    public static final String FIELD_EXCLUSION_PREFIX = "-";

    private final String jql;
    private final Integer startAt;
    private final Integer maxResults;
    private final List<String> fields;
    private final String validateQuery;
    private final List<String> expand;
    private final List<String> properties;
    private final Boolean fieldsByKeys;

    public IssueSearchRequestModel(String jql, Integer startAt, Integer maxResults, List<String> fields, QueryValidationStrategy validationStrategy, List<ExpandableTypes> typesToExpand, List<String> properties, Boolean fieldsByKeys) {
        this.jql = jql;
        this.startAt = startAt;
        this.maxResults = maxResults;
        this.fields = fields;
        this.validateQuery = initialValidationStrategy(validationStrategy);
        this.expand = initialTypesToExpand(typesToExpand);
        this.properties = properties;
        this.fieldsByKeys = fieldsByKeys;
    }

    public String getJql() {
        return jql;
    }

    public Integer getStartAt() {
        return startAt;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public List<String> getFields() {
        return fields;
    }

    public String getValidateQuery() {
        return validateQuery;
    }

    public List<String> getExpand() {
        return expand;
    }

    public List<String> getProperties() {
        return properties;
    }

    public Boolean getFieldsByKeys() {
        return fieldsByKeys;
    }

    private String initialValidationStrategy(QueryValidationStrategy validationStrategy) {
        return null != validationStrategy ? validationStrategy.toString() : QueryValidationStrategy.STRICT.toString();
    }

    private List<String> initialTypesToExpand(List<ExpandableTypes> typesToExpand) {
        return typesToExpand
                   .stream()
                   .map(ExpandableTypes::toString)
                   .collect(Collectors.toList());
    }

}
