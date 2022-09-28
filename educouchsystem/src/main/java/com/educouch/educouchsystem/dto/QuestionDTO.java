package com.educouch.educouchsystem.dto;

public class QuestionDTO {

    private Long questionId;

    private String questionContent;

    private String questionHint;

    private Double questionMaxScore;

    private String questionType;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionContent, String questionHint, Double questionMaxScore, String questionType, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.questionContent = questionContent;
        this.questionHint = questionHint;
        this.questionMaxScore = questionMaxScore;
        this.questionType = questionType;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionHint() {
        return questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    public Double getQuestionMaxScore() {
        return questionMaxScore;
    }

    public void setQuestionMaxScore(Double questionMaxScore) {
        this.questionMaxScore = questionMaxScore;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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
