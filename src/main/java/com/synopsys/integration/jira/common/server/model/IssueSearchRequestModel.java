/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.server.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.synopsys.integration.jira.common.model.request.JiraRequestModel;

public class IssueSearchRequestModel extends JiraRequestModel {
    public static final int DEFAULT_START_AT = 0;
    public static final int DEFAULT_MAX_RESULTS = 50;
    public static final String ALL_FIELDS_OPTION = "*all";
    public static final List<String> ALL_FIELDS_LIST = Collections.singletonList(ALL_FIELDS_OPTION);
    public static final String NAVIGABLE_FIELDS_OPTION = "*navigable";
    public static final List<String> NAVIGABLE_FIELDS_LIST = Collections.singletonList(NAVIGABLE_FIELDS_OPTION);
    public static final String FIELD_EXCLUSION_PREFIX = "-";

    private String jql;
    private Integer startAt;
    private Integer maxResults;
    private Boolean validateQuery;
    private List<String> fields;
    private List<String> expand;

    public static final IssueSearchRequestModel withDefaults(String jql) {
        return new IssueSearchRequestModel(jql, DEFAULT_START_AT, DEFAULT_MAX_RESULTS, true, null, null);
    }

    public static final IssueSearchRequestModel paged(String jql, Integer startAt, Integer maxResults) {
        return new IssueSearchRequestModel(jql, startAt, maxResults, true, null, null);
    }

    /**
     * @param jql           A JQL query string with <a href="https://confluence.atlassian.com/jiracoreserver080/advanced-searching-967898336.html#Advancedsearching-restrictionsRestrictedwordsandcharacters">special characters</a> pre-encoded.
     * @param startAt       The index of the first issue to return (0-based). Default: 0.
     * @param maxResults    The maximum number of issues to return. The maximum allowable value is dictated by the JIRA property 'jira.search.views.default.max'. <br />
     *                      If you specify a value that is higher than this number, your search results will be truncated. Default: 50.
     * @param validateQuery Whether to validate the JQL query. Default: true.
     * @param fields        The list of fields to return for each issue. Default: [{@value #NAVIGABLE_FIELDS_OPTION}] (i.e. "*navigable").
     * @param expand        The list of the parameters to expand. Default: {@value #NAVIGABLE_FIELDS_OPTION}.
     */
    public IssueSearchRequestModel(String jql, Integer startAt, Integer maxResults, Boolean validateQuery, List<String> fields, List<String> expand) {
        this.jql = jql;
        this.startAt = ObjectUtils.defaultIfNull(startAt, DEFAULT_START_AT);
        this.maxResults = ObjectUtils.defaultIfNull(maxResults, DEFAULT_MAX_RESULTS);
        this.validateQuery = ObjectUtils.defaultIfNull(validateQuery, Boolean.TRUE);
        this.fields = fields;
        this.expand = expand;
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

    public Boolean getValidateQuery() {
        return validateQuery;
    }

    public List<String> getFields() {
        return fields;
    }

    public List<String> getExpand() {
        return expand;
    }

}
