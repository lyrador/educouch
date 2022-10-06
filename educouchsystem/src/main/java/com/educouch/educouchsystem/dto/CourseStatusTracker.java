package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.ClassRun;

public class CourseStatusTracker {
    private String status;
    private ClassRun classRun;

    public CourseStatusTracker(String status) {
        this.status = status;
    }

    public CourseStatusTracker(String status, ClassRun classRun) {
        this.status = status;
        this.classRun = classRun;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClassRun getClassRun() {
        return classRun;
    }

    public void setClassRun(ClassRun classRun) {
        this.classRun = classRun;
    }
}
