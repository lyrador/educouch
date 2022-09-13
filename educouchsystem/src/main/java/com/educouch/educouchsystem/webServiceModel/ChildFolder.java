package com.educouch.educouchsystem.webServiceModel;

import com.educouch.educouchsystem.model.Folder;

public class ChildFolder {
    private Long courseId;
    private Long parentFolderId;
    private Folder folder;



    public ChildFolder() {
    }

    public ChildFolder(Long courseId, Long parentFolderId, Folder folder) {
        this.courseId = courseId;
        this.parentFolderId = parentFolderId;
        this.folder = folder;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
