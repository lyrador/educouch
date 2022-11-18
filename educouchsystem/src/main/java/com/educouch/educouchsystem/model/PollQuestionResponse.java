package com.educouch.educouchsystem.model;

import javax.persistence.*;

@Entity
public class PollQuestionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollQuestionResponseId;

    @Column(name="responseAnswer", nullable = false)
    private String responseAnswer;

    @ManyToOne
    @JoinColumn(name="pollQuestion_id")
    private PollQuestion pollQuestion;

    @ManyToOne
    @JoinColumn(name="learner_id")
    private Learner learner;

    public PollQuestionResponse(String responseAnswer) {
        this.responseAnswer = responseAnswer;
    }

    public PollQuestionResponse() {
    }

    public Long getPollQuestionResponseId() {
        return pollQuestionResponseId;
    }

    public void setPollQuestionResponseId(Long pollQuestionResponseId) {
        this.pollQuestionResponseId = pollQuestionResponseId;
    }

    public String getResponseAnswer() {
        return responseAnswer;
    }

    public void setResponseAnswer(String responseAnswer) {
        this.responseAnswer = responseAnswer;
    }

    public PollQuestion getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(PollQuestion pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }
}
