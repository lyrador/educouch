package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.TriviaQuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TriviaQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triviaQuestionId;

    @Column(name="questionTitle", nullable = false)
    private String questionTitle;

    @Column(name="questionNumber", nullable = false)
    private Integer questionNumber;

    @Column(name="hasTimeLimit", nullable = false)
    private Boolean hasTimeLimit;

    @Column(name="questionTimeLimit")
    private Double questionTimeLimit;

    @Column(name="questionIsValid")
    private Boolean questionIsValid;

    private TriviaQuestionTypeEnum triviaQuestionType;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @ManyToOne
    @JoinColumn(name="triviaQuiz_id")
    private TriviaQuiz triviaQuiz;

    @OneToMany(mappedBy = "triviaQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TriviaOption> triviaOptions;

    @OneToMany(mappedBy = "triviaQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TriviaQuestionResponse> triviaQuestionResponses;

    public TriviaQuestion(String questionTitle, Integer questionNumber, Boolean hasTimeLimit, Double questionTimeLimit, Attachment attachment) {
        this.questionTitle = questionTitle;
        this.questionNumber = questionNumber;
        this.hasTimeLimit = hasTimeLimit;
        this.questionTimeLimit = questionTimeLimit;
        this.attachment = attachment;
    }

    public TriviaQuestion(String questionTitle, Boolean hasTimeLimit, Double questionTimeLimit) {
        this.questionTitle = questionTitle;
        this.hasTimeLimit = hasTimeLimit;
        this.questionTimeLimit = questionTimeLimit;
    }

    public TriviaQuestion() {
        this.triviaOptions = new ArrayList<>();
        this.triviaQuestionResponses = new ArrayList<>();
    }

    public Long getTriviaQuestionId() {
        return triviaQuestionId;
    }

    public void setTriviaQuestionId(Long triviaQuestionId) {
        this.triviaQuestionId = triviaQuestionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Boolean getHasTimeLimit() {
        return hasTimeLimit;
    }

    public void setHasTimeLimit(Boolean hasTimeLimit) {
        this.hasTimeLimit = hasTimeLimit;
    }

    public Double getQuestionTimeLimit() {
        return questionTimeLimit;
    }

    public void setQuestionTimeLimit(Double questionTimeLimit) {
        this.questionTimeLimit = questionTimeLimit;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    @JsonIgnore
    public TriviaQuiz getTriviaQuiz() {
        return triviaQuiz;
    }

    public void setTriviaQuiz(TriviaQuiz triviaQuiz) {
        this.triviaQuiz = triviaQuiz;
    }

    public List<TriviaOption> getTriviaOptions() {
        return triviaOptions;
    }

    public void setTriviaOptions(List<TriviaOption> triviaOptions) {
        this.triviaOptions = triviaOptions;
    }

    public List<TriviaQuestionResponse> getTriviaQuestionResponses() {
        return triviaQuestionResponses;
    }

    public void setTriviaQuestionResponses(List<TriviaQuestionResponse> triviaQuestionResponses) {
        this.triviaQuestionResponses = triviaQuestionResponses;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Boolean getQuestionIsValid() {
        return questionIsValid;
    }

    public void setQuestionIsValid(Boolean questionIsValid) {
        this.questionIsValid = questionIsValid;
    }

    public TriviaQuestionTypeEnum getTriviaQuestionType() {
        return triviaQuestionType;
    }

    public void setTriviaQuestionType(TriviaQuestionTypeEnum triviaQuestionType) {
        this.triviaQuestionType = triviaQuestionType;
    }
}
