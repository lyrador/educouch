package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;
    @NotBlank(message = "File original name is mandatory")
    private String fileOriginalName;
    @NotBlank(message = "File storage name is mandatory")
    private String fileStorageName;
    @NotBlank(message = "File type is mandatory")
    private String fileType;
    @NotBlank(message = "File url is mandatory")
    private String fileURL;

    @ManyToOne
    @JoinColumn(name="interactivePage_id")
    private InteractivePage interactivePage;

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

    public InteractivePage getInteractivePage() {
        return interactivePage;
    }

    public void setInteractivePage(InteractivePage interactivePage) {
        this.interactivePage = interactivePage;
    }
}
