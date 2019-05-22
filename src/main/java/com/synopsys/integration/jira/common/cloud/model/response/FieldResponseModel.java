package com.synopsys.integration.jira.common.cloud.model.response;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.model.SchemaComponent;
import com.synopsys.integration.jira.common.model.JiraResponse;

public class FieldResponseModel extends JiraResponse {
    private String id;
    private String key;
    private String name;
    private Boolean custom;
    private Boolean navigable;
    private Boolean searchable;
    private List<String> clauseNames;
    private SchemaComponent schema;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Boolean getCustom() {
        return custom;
    }

    public Boolean getNavigable() {
        return navigable;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public List<String> getClauseNames() {
        return clauseNames;
    }

    public SchemaComponent getSchema() {
        return schema;
    }

}
