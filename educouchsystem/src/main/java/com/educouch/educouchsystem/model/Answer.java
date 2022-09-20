package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @NotNull
    private String answerContent;

    @NotNull
    private Double maxScore;

    @NotNull
    private Boolean isCorrectAnswer;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Question question;

    @OneToOne(mappedBy = "givenAnswer")
    private QuestionAttempt questionAttempt;

    public Answer() {
    }

    public Answer(String answerContent, Double maxScore, Boolean isCorrectAnswer, Question question, QuestionAttempt questionAttempt) {
        this();
        this.answerContent = answerContent;
        this.maxScore = maxScore;
        this.isCorrectAnswer = isCorrectAnswer;
        this.question = question;
        this.questionAttempt = questionAttempt;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Boolean getCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionAttempt getQuestionAttempt() {
        return questionAttempt;
    }

    public void setQuestionAttempt(QuestionAttempt questionAttempt) {
        this.questionAttempt = questionAttempt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerId != null ? answerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.answerId == null && other.answerId != null) || (this.answerId != null && !this.answerId.equals(other.answerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Answer[ id=" + answerId + " ]";
    }

}
