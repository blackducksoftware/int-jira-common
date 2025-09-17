package com.blackduck.integration.jira.common.cloud.model;

import java.util.List;
import java.util.Objects;

public class AtlassianDocumentFormatModel {
    private final String type;
    private final int version;
    private List<AtlassianDocumentNodeModel> content;

    public AtlassianDocumentFormatModel(String type, int version,  List<AtlassianDocumentNodeModel> content) {
        this.type = type;
        this.version = version;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public int getVersion() {
        return version;
    }

    public List<AtlassianDocumentNodeModel> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AtlassianDocumentFormatModel that = (AtlassianDocumentFormatModel) o;
        return version == that.version
                && Objects.equals(type, that.type)
                && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, version, content);
    }
}
