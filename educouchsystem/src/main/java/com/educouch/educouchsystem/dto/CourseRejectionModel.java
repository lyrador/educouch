package com.educouch.educouchsystem.dto;

public class CourseRejectionModel {
    Long courseId;
    String rejectionReason;

    public CourseRejectionModel(Long courseId, String rejectionReason) {
        this.courseId = courseId;
        this.rejectionReason = rejectionReason;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
