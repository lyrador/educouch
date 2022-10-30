package com.educouch.educouchsystem.dto;

public class FileItemDTO {
    private Long attachmentId;

    public FileItemDTO() {
    }

    public FileItemDTO(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
