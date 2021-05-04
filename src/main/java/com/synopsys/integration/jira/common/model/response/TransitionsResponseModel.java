/*
 * int-jira-common
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.List;
import java.util.Optional;

import com.synopsys.integration.jira.common.model.JiraResponseModel;
import com.synopsys.integration.jira.common.model.components.TransitionComponent;

public class TransitionsResponseModel extends JiraResponseModel {
    private String expand;
    private List<TransitionComponent> transitions;

    public TransitionsResponseModel() {
    }

    public TransitionsResponseModel(String expand, List<TransitionComponent> transitions) {
        this.expand = expand;
        this.transitions = transitions;
    }

    public String getExpand() {
        return expand;
    }

    public List<TransitionComponent> getTransitions() {
        return transitions;
    }

    public final Optional<TransitionComponent> findFirstTransitionByName(String name) {
        return getTransitions().stream()
                   .filter(transition -> name.equals(transition.getName()))
                   .findFirst();
    }
}
