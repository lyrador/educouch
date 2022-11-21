package com.educouch.educouchsystem.dto;

public class FolderData {

    private String folderId;
    private String folderName;

    public FolderData(String folderId, String folderName) {
        this.folderId = folderId;
        this.folderName = folderName;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
