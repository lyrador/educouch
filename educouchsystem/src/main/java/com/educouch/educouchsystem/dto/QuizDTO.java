package com.educouch.educouchsystem.dto;

public class QuizDTO {

    private Long assessmentId;

    private String assessmentTitle;

    private String assessmentDescription;

    private Double assessmentMaxScore;

    private Double assessmentActualScore;

    private String assessmentStartDate;

    private String assessmentEndDate;

    private String assessmentIsOpen;

    private String assessmentStatusEnum;

    private String assessmentHasTimeLimit;

    private String assessmentIsAutoRelease;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    public QuizDTO() {
    }

    public QuizDTO(String assessmentTitle, String assessmentDescription, Double assessmentMaxScore, Double assessmentActualScore, String assessmentStartDate, String assessmentEndDate, String assessmentIsOpen, String assessmentStatusEnum, String assessmentHasTimeLimit, String assessmentIsAutoRelease, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDescription = assessmentDescription;
        this.assessmentMaxScore = assessmentMaxScore;
        this.assessmentActualScore = assessmentActualScore;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentIsOpen = assessmentIsOpen;
        this.assessmentStatusEnum = assessmentStatusEnum;
        this.assessmentHasTimeLimit = assessmentHasTimeLimit;
        this.assessmentIsAutoRelease = assessmentIsAutoRelease;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentDescription() {
        return assessmentDescription;
    }

    public void setAssessmentDescription(String assessmentDescription) {
        this.assessmentDescription = assessmentDescription;
    }

    public Double getAssessmentMaxScore() {
        return assessmentMaxScore;
    }

    public void setAssessmentMaxScore(Double assessmentMaxScore) {
        this.assessmentMaxScore = assessmentMaxScore;
    }

    public Double getAssessmentActualScore() {
        return assessmentActualScore;
    }

    public void setAssessmentActualScore(Double assessmentActualScore) {
        this.assessmentActualScore = assessmentActualScore;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public String getAssessmentIsOpen() {
        return assessmentIsOpen;
    }

    public void setAssessmentIsOpen(String assessmentIsOpen) {
        this.assessmentIsOpen = assessmentIsOpen;
    }

    public String getAssessmentStatusEnum() {
        return assessmentStatusEnum;
    }

    public void setAssessmentStatusEnum(String assessmentStatusEnum) {
        this.assessmentStatusEnum = assessmentStatusEnum;
    }

    public String getAssessmentHasTimeLimit() {
        return assessmentHasTimeLimit;
    }

    public void setAssessmentHasTimeLimit(String assessmentHasTimeLimit) {
        this.assessmentHasTimeLimit = assessmentHasTimeLimit;
    }

    public String getAssessmentIsAutoRelease() {
        return assessmentIsAutoRelease;
    }

    public void setAssessmentIsAutoRelease(String assessmentIsAutoRelease) {
        this.assessmentIsAutoRelease = assessmentIsAutoRelease;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
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
