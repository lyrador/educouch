package com.educouch.educouchsystem.dto;

public class QuestionAttemptDTO {

    private Long questionAttemptId;
    private String questionAttemptedQuestionId;
    private Double questionAttemptScore;
    private String shortAnswerResponse;
    private QuestionDTO questionAttempted;
    private String optionSelected;

    private String feedback;


    public QuestionAttemptDTO() {
        questionAttemptScore = 0.0;
        shortAnswerResponse = "";
        feedback = "";
        optionSelected = new String();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getQuestionAttemptId() {
        return questionAttemptId;
    }

    public void setQuestionAttemptId(Long questionAttemptId) {
        this.questionAttemptId = questionAttemptId;
    }

    public Double getQuestionAttemptScore() {
        return questionAttemptScore;
    }

    public void setQuestionAttemptScore(Double questionAttemptScore) {
        this.questionAttemptScore = questionAttemptScore;
    }

    public String getShortAnswerResponse() {
        return shortAnswerResponse;
    }

    public void setShortAnswerResponse(String shortAnswerResponse) {
        this.shortAnswerResponse = shortAnswerResponse;
    }

    public QuestionDTO getQuestionAttempted() {
        return questionAttempted;
    }

    public void setQuestionAttempted(QuestionDTO questionAttempted) {
        this.questionAttempted = questionAttempted;
    }

    public String getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(String optionSelected) {
        this.optionSelected = optionSelected;
    }

    public String getQuestionAttemptedQuestionId() {
        return questionAttemptedQuestionId;
    }

    public void setQuestionAttemptedQuestionId(String questionAttemptedQuestionId) {
        this.questionAttemptedQuestionId = questionAttemptedQuestionId;
    }


}
