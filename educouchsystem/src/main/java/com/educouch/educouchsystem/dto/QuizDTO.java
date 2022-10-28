package com.educouch.educouchsystem.dto;

import java.util.List;

public class QuizDTO {

    private Long assessmentId;
    private String assessmentTitle;
    private String assessmentDescription;
    private Double assessmentMaxScore;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String assessmentIsOpen;
    private String assessmentStatusEnum;
    private String hasTimeLimit;
    private Integer questionCounter;
    private Integer timeLimit;
    private String isAutoRelease;
    private String hasMaxAttempts;
    private Integer maxAttempts;
    private String createdDateTime;
    private Long createdByUserId;
    private String createdByUserName;
    private String createdByUserType;
    private String isExpired;
    private List<QuestionDTO> questions;


    public QuizDTO() {
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

    public String getHasTimeLimit() {
        return hasTimeLimit;
    }

    public void setHasTimeLimit(String hasTimeLimit) {
        this.hasTimeLimit = hasTimeLimit;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getIsAutoRelease() {
        return isAutoRelease;
    }

    public void setIsAutoRelease(String isAutoRelease) {
        this.isAutoRelease = isAutoRelease;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Integer getQuestionCounter() {
        return questionCounter;
    }

    public void setQuestionCounter(Integer questionCounter) {
        this.questionCounter = questionCounter;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getHasMaxAttempts() {
        return hasMaxAttempts;
    }

    public void setHasMaxAttempts(String hasMaxAttempts) {
        this.hasMaxAttempts = hasMaxAttempts;
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


    public String getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(String isExpired) {
        this.isExpired = isExpired;
    }
}
