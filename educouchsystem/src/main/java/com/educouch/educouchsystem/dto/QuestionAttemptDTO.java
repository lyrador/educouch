package com.educouch.educouchsystem.dto;

public class QuestionAttemptDTO {

    private Long questionAttemptId;
    private Double questionAttemptScore;
    private String shortAnswerResponse;
    private QuestionDTO questionAttempted;
    private String optionSelected;


    public QuestionAttemptDTO() {
        questionAttemptScore = 0.0;
        shortAnswerResponse = "";
        optionSelected = new String();
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
}
