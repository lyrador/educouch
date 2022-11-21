package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Quiz extends Assessment implements Serializable {

    @NotNull
    private Boolean hasTimeLimit;

    private Integer timeLimit;

    @NotNull
    private Integer questionCounter;

    @NotNull
    private Boolean isAutoRelease;

    @NotNull
    private Boolean hasMaxAttempts;

    private Integer maxAttempts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="quiz_id")
    private List<Question> quizQuestions;

    @OneToOne
    @JoinColumn(name="preview_attempt_id")
    private QuizAttempt previewAttempt;

    @OneToOne(mappedBy = "pageQuiz")
    private InteractivePage interactivePage;

    public Quiz() {
        super();
        this.quizQuestions = new ArrayList<>();
    }

    public Quiz(String title, String description, Double maxScore, Long discountPointForAssessment,Integer discountPointToTopPercent, Date startDate, Date endDate, Boolean hasTimeLimit, Integer timeLimit, Boolean isAutoRelease) {
        super(title, description, maxScore, startDate, endDate,discountPointForAssessment,discountPointToTopPercent);
        this.hasTimeLimit = hasTimeLimit;
        this.isAutoRelease = isAutoRelease;
        this.quizQuestions = new ArrayList<>();
    }

    public Boolean getHasTimeLimit() {
        return hasTimeLimit;
    }

    public void setHasTimeLimit(Boolean hasTimeLimit) {
        this.hasTimeLimit = hasTimeLimit;
    }

    public Boolean getAutoRelease() {
        return isAutoRelease;
    }

    public void setAutoRelease(Boolean autoRelease) {
        isAutoRelease = autoRelease;
    }

    public List<Question> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<Question> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
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

    public Boolean getHasMaxAttempts() {
        return hasMaxAttempts;
    }

    public void setHasMaxAttempts(Boolean hasMaxAttempts) {
        this.hasMaxAttempts = hasMaxAttempts;
    }

    @JsonIgnore
    public InteractivePage getInteractivePage() {
        return interactivePage;
    }

    public void setInteractivePage(InteractivePage interactivePage) {
        this.interactivePage = interactivePage;
    }

    public QuizAttempt getPreviewAttempt() {
        return previewAttempt;
    }

    public void setPreviewAttempt(QuizAttempt previewAttempt) {
        this.previewAttempt = previewAttempt;
    }
}
