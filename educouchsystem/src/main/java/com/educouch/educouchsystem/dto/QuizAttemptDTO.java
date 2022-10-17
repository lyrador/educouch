package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;

import java.util.Date;
import java.util.List;

public class QuizAttemptDTO {
    private Long quizAttemptId;
    private Integer attemptCounter;
    private Double obtainedScore;
    private List<QuestionAttemptDTO> questionAttempts;
    private String lastAttemptTime;
    private Integer timeLimitRemaining;
    private String assessmentAttemptStatusEnum; //INCOMPLETE, SUBMITTED, GRADED
    private QuizDTO attemptedQuiz;


    public QuizAttemptDTO() {
        this.attemptCounter = 1;
        this.obtainedScore = 0.0;
        this.assessmentAttemptStatusEnum = "INCOMPLETE";
    }

    public Long getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(Long quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }

    public Integer getAttemptCounter() {
        return attemptCounter;
    }

    public void setAttemptCounter(Integer attemptCounter) {
        this.attemptCounter = attemptCounter;
    }

    public Double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(Double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public List<QuestionAttemptDTO> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttemptDTO> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }

    public Integer getTimeLimitRemaining() {
        return timeLimitRemaining;
    }

    public void setTimeLimitRemaining(Integer timeLimitRemaining) {
        this.timeLimitRemaining = timeLimitRemaining;
    }

    public String getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(String lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    public String getAssessmentAttemptStatusEnum() {
        return assessmentAttemptStatusEnum;
    }

    public void setAssessmentAttemptStatusEnum(String assessmentAttemptStatusEnum) {
        this.assessmentAttemptStatusEnum = assessmentAttemptStatusEnum;
    }

    public QuizDTO getAttemptedQuiz() {
        return attemptedQuiz;
    }

    public void setAttemptedQuiz(QuizDTO attemptedQuiz) {
        this.attemptedQuiz = attemptedQuiz;
    }
}
