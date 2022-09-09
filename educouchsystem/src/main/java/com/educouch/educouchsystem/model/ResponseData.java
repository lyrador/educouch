package com.educouch.educouchsystem.model;

public class ResponseData {

    private Long fileId;
    private String fileOriginalName;

    private String fileStorageName;
    private String fileURL;
    private String fileType;
    private Long fileSize;

    public ResponseData() {
    }

    public ResponseData(Long fileId, String fileOriginalName, String fileStorageName, String fileURL, String fileType, Long fileSize) {
        this.fileId = fileId;
        this.fileOriginalName = fileOriginalName;
        this.fileStorageName = fileStorageName;
        this.fileURL = fileURL;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) { this.fileId = fileId; }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileStorageName() { return fileStorageName; }

    public void setFileStorageName(String fileStorageName) { this.fileStorageName = fileStorageName; }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
