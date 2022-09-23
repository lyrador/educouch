package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.QuestionTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @NotNull
    private String questionContent;

    @NotNull
    private String questionHint;

    @NotNull
    private Double questionMaxScore;

    @Enumerated(EnumType.STRING)
    @NotNull
    private QuestionTypeEnum questionType;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "optionQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    @OneToMany(mappedBy = "questionAttempted", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAttempt> questionAttempts;

    @OneToOne
    private OpenEnded openEnded;

    public Question() {
        this.options = new ArrayList<>();
        this.questionAttempts = new ArrayList<>();
    }

    public Question(String questionContent, String questionHint, Double questionMaxScore, QuestionTypeEnum questionType, Quiz quiz) {
        this();
        this.questionContent = questionContent;
        this.questionHint = questionHint;
        this.questionMaxScore = questionMaxScore;
        this.questionType = questionType;
        this.quiz = quiz;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionHint() {
        return questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    public Double getQuestionMaxScore() {
        return questionMaxScore;
    }

    public void setQuestionMaxScore(Double questionMaxScore) {
        this.questionMaxScore = questionMaxScore;
    }

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public OpenEnded getOpenEnded() {
        return openEnded;
    }

    public void setOpenEnded(OpenEnded openEnded) {
        this.openEnded = openEnded;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Question[ id=" + questionId + " ]";
    }

}
