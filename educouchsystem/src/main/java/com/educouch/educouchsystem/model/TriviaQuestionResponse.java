package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TriviaQuestionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triviaQuestionResponseId;

    @ManyToOne
    @JoinColumn(name="triviaQuestion_id")
    private TriviaQuestion triviaQuestion;

    @OneToOne
    @JoinColumn(name = "triviaOption_id")
    private TriviaOption optionChosen;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    public TriviaQuestionResponse() {
    }

    public Long getTriviaQuestionResponseId() {
        return triviaQuestionResponseId;
    }

    public void setTriviaQuestionResponseId(Long triviaQuestionResponseId) {
        this.triviaQuestionResponseId = triviaQuestionResponseId;
    }

    @JsonIgnore
    public TriviaQuestion getTriviaQuestion() {
        return triviaQuestion;
    }

    public void setTriviaQuestion(TriviaQuestion triviaQuestion) {
        this.triviaQuestion = triviaQuestion;
    }

    public TriviaOption getOptionChosen() {
        return optionChosen;
    }

    public void setOptionChosen(TriviaOption optionChosen) {
        this.optionChosen = optionChosen;
    }

    @JsonIgnore
    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }
}
