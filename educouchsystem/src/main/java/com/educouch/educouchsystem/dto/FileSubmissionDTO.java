package com.educouch.educouchsystem.dto;

import java.util.Date;

public class FileSubmissionDTO {

    private Long assessmentId;

    private String assessmentTitle;

    private String assessmentDescription;

    private Double assessmentMaxScore;

    private Long discountPointForAssessment;

    private Integer discountPointToTopPercent;

    private Double assessmentActualScore;

    private String assessmentStartDate;

    private String assessmentEndDate;

    private String assessmentIsOpen;

    private String assessmentStatusEnum;

    private String assessmentFileSubmissionEnum;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    public FileSubmissionDTO() {
    }

    public FileSubmissionDTO(String assessmentTitle, String assessmentDescription, Double assessmentMaxScore, Double assessmentActualScore, String assessmentStartDate, String assessmentEndDate, String assessmentIsOpen, String assessmentStatusEnum, String assessmentFileSubmissionEnum, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType, Long discountPointForAssessment, Integer discountPointToTopPercent) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDescription = assessmentDescription;
        this.assessmentMaxScore = assessmentMaxScore;
        this.assessmentActualScore = assessmentActualScore;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentIsOpen = assessmentIsOpen;
        this.assessmentStatusEnum = assessmentStatusEnum;
        this.assessmentFileSubmissionEnum = assessmentFileSubmissionEnum;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
        this.discountPointForAssessment = discountPointForAssessment;
        this.discountPointToTopPercent = discountPointToTopPercent;
    }

    public Long getDiscountPointForAssessment() {
        return discountPointForAssessment;
    }

    public void setDiscountPointForAssessment(Long discountPointForAssessment) {
        this.discountPointForAssessment = discountPointForAssessment;
    }

    public Integer getDiscountPointToTopPercent() {
        return discountPointToTopPercent;
    }

    public void setDiscountPointToTopPercent(Integer discountPointToTopPercent) {
        this.discountPointToTopPercent = discountPointToTopPercent;
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

    public String getAssessmentFileSubmissionEnum() {
        return assessmentFileSubmissionEnum;
    }

    public void setAssessmentFileSubmissionEnum(String assessmentFileSubmissionEnum) {
        this.assessmentFileSubmissionEnum = assessmentFileSubmissionEnum;
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
