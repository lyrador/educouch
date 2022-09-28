package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuizAttempt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizAttemptId;

    @NotNull
    private String quizAttemptDescription;

    @NotNull
    private Double obtainedScore = 0.0;

    @OneToMany(mappedBy = "quizAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAttempt> questionAttempts;

    public QuizAttempt() {

    }

    public QuizAttempt(String quizAttemptDescription) {
        this();
        this.quizAttemptDescription = quizAttemptDescription;
    }

    public Long getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(Long quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }

    public Double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(Double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public String getQuizAttemptDescription() {
        return quizAttemptDescription;
    }

    public void setQuizAttemptDescription(String quizAttemptDescription) {
        this.quizAttemptDescription = quizAttemptDescription;
    }

    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttempt> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quizAttemptId != null ? quizAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof QuizAttempt)) {
            return false;
        }
        QuizAttempt other = (QuizAttempt) object;
        if ((this.quizAttemptId == null && other.quizAttemptId != null) || (this.quizAttemptId != null && !this.quizAttemptId.equals(other.quizAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.QuizAttempt[ id=" + quizAttemptId + " ]";
    }
}
