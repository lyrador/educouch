package com.educouch.educouchsystem.dto;

public class CourseStatusTracker {
    private String status;

    public CourseStatusTracker(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
