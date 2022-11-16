package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TriviaOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triviaOptionId;

    @Column(name="optionContent", nullable = false)
    private String optionContent;

    @Column(name="correctAnswer", nullable = false)
    private Boolean correctAnswer;

    @Column(name="optionNumber", nullable = false)
    private Integer optionNumber;

    @ManyToOne
    @JoinColumn(name="triviaQuestion_id")
    private TriviaQuestion triviaQuestion;

    public TriviaOption(String optionContent, Boolean correctAnswer) {
        this.optionContent = optionContent;
        this.correctAnswer = correctAnswer;
    }

    public TriviaOption() {
    }

    public Long getTriviaOptionId() {
        return triviaOptionId;
    }

    public void setTriviaOptionId(Long triviaOptionId) {
        this.triviaOptionId = triviaOptionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @JsonIgnore
    public TriviaQuestion getTriviaQuestion() {
        return triviaQuestion;
    }

    public void setTriviaQuestion(TriviaQuestion triviaQuestion) {
        this.triviaQuestion = triviaQuestion;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Integer optionNumber) {
        this.optionNumber = optionNumber;
    }
}
