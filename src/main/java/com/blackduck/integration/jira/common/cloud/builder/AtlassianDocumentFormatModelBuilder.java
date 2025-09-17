package com.blackduck.integration.jira.common.cloud.builder;

import com.blackduck.integration.jira.common.cloud.model.AtlassianDocumentFormatModel;
import com.blackduck.integration.jira.common.cloud.model.AtlassianDocumentNodeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtlassianDocumentFormatModelBuilder {
    public static final String DOCUMENT_NODE_ATTRIBUTE_TYPE = "type";
    public static final String DOCUMENT_NODE_TYPE_PARAGRAPH = "paragraph";
    public static final String DOCUMENT_NODE_ATTRIBUTE_TEXT = "text";
    public static final String DOCUMENT_NODE_TYPE_TEXT = "text";
    public static final String DEFAULT_DOCUMENT_TYPE = "doc";
    public static final int DEFAULT_DOCUMENT_VERSION = 1;
    private String documentType;
    private int documentVersion;
    private ArrayList<AtlassianDocumentNodeModel> documentContent;

    public AtlassianDocumentFormatModelBuilder() {
        documentType(DEFAULT_DOCUMENT_TYPE);
        documentVersion(DEFAULT_DOCUMENT_VERSION);
        this.documentContent = new ArrayList<>();
    }

    public AtlassianDocumentFormatModelBuilder documentType(String documentType) {
        this.documentType = documentType;
        return this;
    }
    public AtlassianDocumentFormatModelBuilder documentVersion(int documentVersion) {
        this.documentVersion = documentVersion;
        return this;
    }

    public AtlassianDocumentFormatModelBuilder addSingleParagraphTextNode(String text) {
        Map<String,Object> descriptionContent = new HashMap<>();
        descriptionContent.put(AtlassianDocumentFormatModelBuilder.DOCUMENT_NODE_ATTRIBUTE_TYPE, AtlassianDocumentFormatModelBuilder.DOCUMENT_NODE_TYPE_TEXT);
        descriptionContent.put(AtlassianDocumentFormatModelBuilder.DOCUMENT_NODE_ATTRIBUTE_TEXT, text);
        return addContentNode(AtlassianDocumentFormatModelBuilder.DOCUMENT_NODE_TYPE_PARAGRAPH, descriptionContent);
    }

    public AtlassianDocumentFormatModelBuilder addContentNode(String type, Map<String, Object> content) {
        List<Map<String, Object>> contentList = new ArrayList<>();
        contentList.add(content);
        return addContentNode(type, contentList);
    }

    public AtlassianDocumentFormatModelBuilder addContentNode(String type, List<Map<String, Object>> content) {
        this.documentContent.add(new AtlassianDocumentNodeModel(type, content));
        return this;
    }

    public AtlassianDocumentFormatModel build() {
        if(documentType == null || documentVersion < 1 || documentContent.isEmpty()) {
            throw new IllegalStateException("Document type and document version and content are mandatory");
        }

        return new AtlassianDocumentFormatModel(documentType, documentVersion, documentContent);
    }
}
