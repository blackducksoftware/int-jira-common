package com.synopsys.integration.jira.common.cloud.model;

import java.util.Map;

import com.synopsys.integration.rest.component.IntRestResponse;

public class TransitionComponent extends IntRestResponse {
    private String id;
    private String name;
    private StatusDetailsComponent to;
    private Boolean hasScreen;
    private Boolean isGlobal;
    private Boolean isInitial;
    private Map<String, Object> fields;
    private String expand;

    public TransitionComponent() {
    }

    public TransitionComponent(final String id, final String name, final StatusDetailsComponent to, final Boolean hasScreen, final Boolean isGlobal, final Boolean isInitial, final Map<String, Object> fields, final String expand) {
        this.id = id;
        this.name = name;
        this.to = to;
        this.hasScreen = hasScreen;
        this.isGlobal = isGlobal;
        this.isInitial = isInitial;
        this.fields = fields;
        this.expand = expand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StatusDetailsComponent getTo() {
        return to;
    }

    public Boolean getHasScreen() {
        return hasScreen;
    }

    public Boolean getGlobal() {
        return isGlobal;
    }

    public Boolean getInitial() {
        return isInitial;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public String getExpand() {
        return expand;
    }
}
