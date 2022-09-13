package com.educouch.educouchsystem.webServiceModel;

import com.educouch.educouchsystem.model.Folder;

public class ParentFolder {
    private Folder folder;

    private Long courseId;



    ParentFolder(Folder folder, Long courseId) {
        this.folder = folder;
        this.courseId = courseId;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }


    ParentFolder() {

    }

}
