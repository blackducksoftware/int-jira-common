package com.synopsys.integration.jira.common.cloud.model.request;

import java.util.List;

import com.synopsys.integration.jira.common.cloud.model.EntityProperty;
import com.synopsys.integration.jira.common.cloud.model.TransitionComponent;
import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueRequestModel extends JiraComponent {
    private TransitionComponent transition;
    private Object fields; // TODO this might have to be a map
    private Object update; // TODO this might have to be a map
    private List<EntityProperty> properties;

    public IssueRequestModel(final TransitionComponent transition, final Object fields, final Object update, final List<EntityProperty> properties) {
        this.transition = transition;
        this.fields = fields;
        this.update = update;
        this.properties = properties;
    }

    public TransitionComponent getTransition() {
        return transition;
    }

    public Object getFields() {
        return fields;
    }

    public Object getUpdate() {
        return update;
    }

    public List<EntityProperty> getProperties() {
        return properties;
    }

}
