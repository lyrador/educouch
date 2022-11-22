package com.educouch.educouchsystem.dto;

public class QuestionToReorderDTO {
    private Long triviaQuestionId;
    private String questionNumber;
    private String questionTitle;

    public QuestionToReorderDTO() {
    }

    public QuestionToReorderDTO(Long triviaQuestionId, String questionNumber, String questionTitle) {
        this.triviaQuestionId = triviaQuestionId;
        this.questionNumber = questionNumber;
        this.questionTitle = questionTitle;
    }

    public Long getTriviaQuestionId() {
        return triviaQuestionId;
    }

    public void setTriviaQuestionId(Long triviaQuestionId) {
        this.triviaQuestionId = triviaQuestionId;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }
}
