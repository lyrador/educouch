package com.educouch.educouchsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;
    private String fileOriginalName;

    private String fileStorageName;
    private String fileType;
    private String fileURL;

    public Attachment() {
    }

    public Attachment(String fileOriginalName, String fileStorageName, String fileType, String fileURL) {
        this.fileOriginalName = fileOriginalName;
        this.fileStorageName = fileStorageName;
        this.fileType = fileType;
        this.fileURL = fileURL;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileStorageName() { return fileStorageName; }

    public void setFileStorageName(String fileStorageName) { this.fileStorageName = fileStorageName; }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) { this.fileURL = fileURL; }
}
