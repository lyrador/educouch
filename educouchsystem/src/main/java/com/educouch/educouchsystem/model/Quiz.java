package com.educouch.educouchsystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz extends Assessment implements Serializable {

    @NotNull
    private Boolean hasTimeLimit;

    @NotNull
    private Boolean isAutoRelease;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> quizQuestions;

    @OneToMany(mappedBy = "quizAttempted", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizAttempt> quizAttempts;

    public Quiz() {
        super();
        this.quizQuestions = new ArrayList<>();
        this.quizAttempts = new ArrayList<>();
    }

    public Quiz(Boolean hasTimeLimit, Boolean isAutoRelease) {
        super();
        this.quizQuestions = new ArrayList<>();
        this.quizAttempts = new ArrayList<>();
        this.hasTimeLimit = hasTimeLimit;
        this.isAutoRelease = isAutoRelease;
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

    public List<QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }
}
