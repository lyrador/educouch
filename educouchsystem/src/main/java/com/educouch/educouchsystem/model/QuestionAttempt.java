package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuestionAttempt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionAttemptId;

    @NotNull
    private Double questionAttemptScore = 0.0;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Question questionAttempted;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToMany
    @JoinTable(name = "QuestionAttempt_Option", joinColumns = {
            @JoinColumn(name = "questionAttemptId")}, inverseJoinColumns = {
            @JoinColumn(name = "optionId")
    })
    private List<Option> learnerOptions;


    public QuestionAttempt() {
        this.learnerOptions = new ArrayList<>();
    }

    public QuestionAttempt(Question questionAttempted, QuizAttempt quizAttempt, List<Option> learnerOptions) {
        this();
        this.questionAttempted = questionAttempted;
        this.quizAttempt = quizAttempt;
        this.learnerOptions = learnerOptions;
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

    public List<Option> getLearnerOptions() {
        return learnerOptions;
    }

    public void setLearnerOptions(List<Option> learnerOptions) {
        this.learnerOptions = learnerOptions;
    }

    public Question getQuestionAttempted() {
        return questionAttempted;
    }

    public void setQuestionAttempted(Question questionAttempted) {
        this.questionAttempted = questionAttempted;
    }

    public QuizAttempt getQuizAttempt() {
        return quizAttempt;
    }

    public void setQuizAttempt(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionAttemptId != null ? questionAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof QuestionAttempt)) {
            return false;
        }
        QuestionAttempt other = (QuestionAttempt) object;
        if ((this.questionAttemptId == null && other.questionAttemptId != null) || (this.questionAttemptId != null && !this.questionAttemptId.equals(other.questionAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.questionAttempt[ id=" + questionAttemptId + " ]";
    }

}
