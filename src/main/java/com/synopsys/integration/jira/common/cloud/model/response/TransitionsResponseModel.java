package com.synopsys.integration.jira.common.cloud.model.response;

import java.util.List;
import java.util.Optional;

import com.synopsys.integration.jira.common.cloud.model.TransitionComponent;
import com.synopsys.integration.jira.common.model.JiraResponse;

public class TransitionsResponseModel extends JiraResponse {
    private String expand;
    private List<TransitionComponent> transitions;

    public TransitionsResponseModel() {
    }

    public TransitionsResponseModel(final String expand, final List<TransitionComponent> transitions) {
        this.expand = expand;
        this.transitions = transitions;
    }

    public String getExpand() {
        return expand;
    }

    public List<TransitionComponent> getTransitions() {
        return transitions;
    }

    public final Optional<TransitionComponent> findFirstTransitionByName(final String name) {
        return getTransitions().stream()
                   .filter(transition -> name.equals(transition.getName()))
                   .findFirst();
    }
}
