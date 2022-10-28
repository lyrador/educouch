package com.educouch.educouchsystem.dto;

import java.util.List;

public class QuestionDTO {

    private String questionId;
    private String localid;
    private String questionTitle;
    private String questionType;
    private String questionContent;
    private String questionHint;
    private String questionMaxPoints;
    private List<String> options;
    private String correctOption;

    public QuestionDTO() {
        this.correctOption = "";
    }

    public QuestionDTO(String localid, String questionTitle, String questionType, String questionContent, String questionMaxPoints, List<String> options, String questionHint) {
        this.localid = localid;
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.questionMaxPoints = questionMaxPoints;
        this.options = options;
        this.questionHint = questionHint;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionMaxPoints() {
        return questionMaxPoints;
    }

    public void setQuestionMaxPoints(String questionMaxPoints) {
        this.questionMaxPoints = questionMaxPoints;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public String getQuestionHint() {
        return questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }
}
