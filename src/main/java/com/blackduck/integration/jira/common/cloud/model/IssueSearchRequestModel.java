/*
 * int-jira-common
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jira.common.cloud.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.blackduck.integration.jira.common.enumeration.ExpandableTypes;
import com.blackduck.integration.jira.common.enumeration.QueryValidationStrategy;
import com.blackduck.integration.jira.common.model.request.JiraRequestModel;
import org.apache.commons.lang3.StringUtils;

public class IssueSearchRequestModel extends JiraRequestModel {
    public static final String ALL_FIELDS_OPTION = "*all";
    public static final List<String> ALL_FIELDS_LIST = Collections.singletonList(ALL_FIELDS_OPTION);
    public static final String NAVIGABLE_FIELDS_OPTION = "*navigable";
    public static final String FIELD_EXCLUSION_PREFIX = "-";

    private final String jql;
    private final Integer maxResults;
    private final List<String> fields;
    private final String expand;
    private final List<String> properties;
    private final Boolean fieldsByKeys;
    private final List<Integer> reconcileIssues;
    private final String nextPageToken;

    public IssueSearchRequestModel(String jql, Integer maxResults, List<String> fields, List<ExpandableTypes> typesToExpand, List<String> properties, Boolean fieldsByKeys, List<Integer> reconcileIssues, String nextPageToken) {
        this.jql = jql;
        this.maxResults = maxResults;
        this.fields = fields;
        this.expand = initialTypesToExpand(typesToExpand);
        this.properties = properties;
        this.fieldsByKeys = fieldsByKeys;
        this.reconcileIssues = reconcileIssues;
        this.nextPageToken = nextPageToken;
    }

    public String getJql() {
        return jql;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public List<String> getFields() {
        return fields;
    }

    public String getExpand() {
        return expand;
    }

    public List<String> getProperties() {
        return properties;
    }

    public Boolean getFieldsByKeys() {
        return fieldsByKeys;
    }

    public List<Integer> getReconcileIssues() {
        return reconcileIssues;
    }

    public Optional<String> getNextPageToken() {
        return Optional.ofNullable(nextPageToken);
    }

    private String initialTypesToExpand(List<ExpandableTypes> typesToExpand) {
        return typesToExpand
                .stream()
                .map(ExpandableTypes::toString)
                .collect(Collectors.joining(","));
    }
}
