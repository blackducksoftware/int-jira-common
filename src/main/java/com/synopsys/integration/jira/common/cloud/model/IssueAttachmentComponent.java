package com.synopsys.integration.jira.common.cloud.model;

import com.synopsys.integration.jira.common.model.JiraComponent;

public class IssueAttachmentComponent extends JiraComponent {
    private Integer id;
    private String self;
    private String fileName;
    private UserDetailsComponent author;
    private String created;
    private Integer size;
    private String mimeType;
    private String content;
    private String thumbnail;

    public IssueAttachmentComponent() {
    }

    public IssueAttachmentComponent(final Integer id, final String self, final String fileName, final UserDetailsComponent author, final String created, final Integer size, final String mimeType, final String content,
        final String thumbnail) {
        this.id = id;
        this.self = self;
        this.fileName = fileName;
        this.author = author;
        this.created = created;
        this.size = size;
        this.mimeType = mimeType;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public Integer getId() {
        return id;
    }

    public String getSelf() {
        return self;
    }

    public String getFileName() {
        return fileName;
    }

    public UserDetailsComponent getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public Integer getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}
