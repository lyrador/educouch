package com.educouch.educouchsystem.dto;
import java.util.List;

public class CourseDTO {

    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private String courseDescription;
    private String courseTimeline;
    private Double courseMaxScore;
    private String rejectionReason;
    private String ageGroup;
    private String courseApprovalStatus;
    private List<String> categoryTags;
    private Long createdByUserId;
    private String createdByUserName;
    private String createdByUserType;

    public CourseDTO() {
    }

    public CourseDTO(Long courseId, String courseCode, String courseTitle, String courseDescription, String courseTimeline, Double courseMaxScore, String rejectionReason, String ageGroup, String courseApprovalStatus, List<String> categoryTags, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseTimeline = courseTimeline;
        this.courseMaxScore = courseMaxScore;
        this.rejectionReason = rejectionReason;
        this.ageGroup = ageGroup;
        this.courseApprovalStatus = courseApprovalStatus;
        this.categoryTags = categoryTags;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseTimeline() {
        return courseTimeline;
    }

    public void setCourseTimeline(String courseTimeline) {
        this.courseTimeline = courseTimeline;
    }

    public Double getCourseMaxScore() {
        return courseMaxScore;
    }

    public void setCourseMaxScore(Double courseMaxScore) {
        this.courseMaxScore = courseMaxScore;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getCourseApprovalStatus() {
        return courseApprovalStatus;
    }

    public void setCourseApprovalStatus(String courseApprovalStatus) {
        this.courseApprovalStatus = courseApprovalStatus;
    }

    public List<String> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(List<String> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getCreatedByUserType() {
        return createdByUserType;
    }

    public void setCreatedByUserType(String createdByUserType) {
        this.createdByUserType = createdByUserType;
    }
}

