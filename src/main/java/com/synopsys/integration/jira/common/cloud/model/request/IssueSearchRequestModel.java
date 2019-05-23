package com.synopsys.integration.jira.common.cloud.model.request;

import java.util.List;
import java.util.stream.Collectors;

import com.synopsys.integration.jira.common.cloud.enumeration.ExpandableTypes;
import com.synopsys.integration.jira.common.cloud.enumeration.QueryValidationStrategy;
import com.synopsys.integration.jira.common.model.JiraSerializable;

public class IssueSearchRequestModel extends JiraSerializable {
    public static final String ALL_FIELDS_OPTION = "*all";
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

    public IssueSearchRequestModel(
        final String jql, final Integer startAt, final Integer maxResults, final List<String> fields, final QueryValidationStrategy validationStrategy, final List<ExpandableTypes> typesToExpand, final List<String> properties,
        final Boolean fieldsByKeys) {
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

    private String initialValidationStrategy(final QueryValidationStrategy validationStrategy) {
        return null != validationStrategy ? validationStrategy.toString() : QueryValidationStrategy.STRICT.toString();
    }

    private List<String> initialTypesToExpand(final List<ExpandableTypes> typesToExpand) {
        return typesToExpand
                   .stream()
                   .map(ExpandableTypes::toString)
                   .collect(Collectors.toList());
    }

}
