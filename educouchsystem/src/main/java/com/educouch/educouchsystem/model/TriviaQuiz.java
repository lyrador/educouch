package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TriviaQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triviaQuizId;

    @Column(name="triviaQuizDescription", nullable = false)
    private String triviaQuizDescription;

    @Column(name="numOfQuestions")
    private Integer numOfQuestions;

    @OneToMany(mappedBy = "triviaQuiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TriviaQuestion> triviaQuestions;

    @ManyToOne
    @JoinColumn(name="classRun_id")
    private ClassRun classRun;

    public TriviaQuiz(String triviaQuizDescription, Integer numOfQuestions) {
        this.triviaQuizDescription = triviaQuizDescription;
        this.numOfQuestions = numOfQuestions;
    }

    public TriviaQuiz() {
        this.triviaQuestions = new ArrayList<>();
    }

    public Long getTriviaQuizId() {
        return triviaQuizId;
    }

    public void setTriviaQuizId(Long triviaQuizId) {
        this.triviaQuizId = triviaQuizId;
    }

    public String getTriviaQuizDescription() {
        return triviaQuizDescription;
    }

    public void setTriviaQuizDescription(String triviaQuizDescription) {
        this.triviaQuizDescription = triviaQuizDescription;
    }

    public Integer getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(Integer numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public List<TriviaQuestion> getTriviaQuestions() {
        return triviaQuestions;
    }

    public void setTriviaQuestions(List<TriviaQuestion> triviaQuestions) {
        this.triviaQuestions = triviaQuestions;
    }

    @JsonIgnore
    public ClassRun getClassRun() {
        return classRun;
    }

    public void setClassRun(ClassRun classRun) {
        this.classRun = classRun;
    }
}
