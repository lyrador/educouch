package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PollQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollQuestionId;

    @Column(name="pollQuestionTitle", nullable = false)
    private Long pollQuestionTitle;

    @Column(name="pollQuestionNumber", nullable = false)
    private Integer pollQuestionNumber;

    @Column(name="hasTimeLimit", nullable = false)
    private Boolean hasTimeLimit;

    @Column(name="questionTimeLimit", nullable = false)
    private Double questionTimeLimit;

    @ManyToOne
    @JoinColumn(name="poll_id")
    private Poll poll;

    @OneToMany(mappedBy = "pollQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PollQuestionResponse> pollQuestionResponses;

    public PollQuestion(Long pollQuestionTitle, Integer pollQuestionNumber, Boolean hasTimeLimit, Double questionTimeLimit) {
        this.pollQuestionTitle = pollQuestionTitle;
        this.pollQuestionNumber = pollQuestionNumber;
        this.hasTimeLimit = hasTimeLimit;
        this.questionTimeLimit = questionTimeLimit;
    }

    public PollQuestion() {
        this.pollQuestionResponses = new ArrayList<>();
    }

    public Long getPollQuestionId() {
        return pollQuestionId;
    }

    public void setPollQuestionId(Long pollQuestionId) {
        this.pollQuestionId = pollQuestionId;
    }

    public Long getPollQuestionTitle() {
        return pollQuestionTitle;
    }

    public void setPollQuestionTitle(Long pollQuestionTitle) {
        this.pollQuestionTitle = pollQuestionTitle;
    }

    public Integer getPollQuestionNumber() {
        return pollQuestionNumber;
    }

    public void setPollQuestionNumber(Integer pollQuestionNumber) {
        this.pollQuestionNumber = pollQuestionNumber;
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

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public List<PollQuestionResponse> getPollQuestionResponses() {
        return pollQuestionResponses;
    }

    public void setPollQuestionResponses(List<PollQuestionResponse> pollQuestionResponses) {
        this.pollQuestionResponses = pollQuestionResponses;
    }
}
