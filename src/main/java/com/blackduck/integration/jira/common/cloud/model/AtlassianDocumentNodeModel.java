package com.blackduck.integration.jira.common.cloud.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AtlassianDocumentNodeModel {
    private final String type;
    private final List<Map<String, Object>> content;

    public AtlassianDocumentNodeModel(String type, List<Map<String, Object>> content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public List<Map<String, Object>> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AtlassianDocumentNodeModel that = (AtlassianDocumentNodeModel) o;
        return Objects.equals(type, that.type)
                && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, content);
    }
}
