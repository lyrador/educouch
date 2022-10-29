package com.educouch.educouchsystem.model;

import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Entity
public class QuestionAttempt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionAttemptId;

    @NotNull
    private Double questionAttemptScore;

    private String shortAnswerResponse;

    private String feedback;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Question questionAttempted;

    @NotNull
    @OneToOne
    private Option optionSelected;

//    @ManyToOne(optional = false)
//    @JoinColumn(nullable = false)
//    private QuizAttempt quizAttempt;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "questionAttempt_id")
//    private List<Option> options;


    public QuestionAttempt() {
        this.questionAttemptScore = 0.0;
        this.shortAnswerResponse = "";
        this.feedback = "";
//        this.optionSelected = new Option("");
    }

    public QuestionAttempt(Question questionAttempted, QuizAttempt quizAttempt) {
        this();
        this.questionAttempted = questionAttempted;
//        this.quizAttempt = quizAttempt;
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

    public Option getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(Option optionSelected) {
        this.optionSelected = optionSelected;
    }

    public String getShortAnswerResponse() {
        return shortAnswerResponse;
    }

    public void setShortAnswerResponse(String shortAnswerResponse) {
        this.shortAnswerResponse = shortAnswerResponse;
    }
    //    public List<Option> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<Option> options) {
//        this.options = options;
//    }

    public Question getQuestionAttempted() {
        return questionAttempted;
    }

    public void setQuestionAttempted(Question questionAttempted) {
        this.questionAttempted = questionAttempted;
    }

//    public QuizAttempt getQuizAttempt() {
//        return quizAttempt;
//    }
//
//    public void setQuizAttempt(QuizAttempt quizAttempt) {
//        this.quizAttempt = quizAttempt;
//    }

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

