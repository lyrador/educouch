package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TriviaQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triviaQuizId;

    @Column(name="triviaQuizTitle", nullable = false)
    private String triviaQuizTitle;

    @Column(name="triviaQuizDescription", nullable = false)
    private String triviaQuizDescription;

    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @Column(name="numOfQuestions")
    private Integer numOfQuestions;

    @OneToMany(mappedBy = "triviaQuiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TriviaQuestion> triviaQuestions;

    @ManyToOne
    @JoinColumn(name="classRun_id")
    private ClassRun classRun;

    public TriviaQuiz(String triviaQuizTitle, String triviaQuizDescription, Integer numOfQuestions) {
        this.triviaQuizTitle = triviaQuizTitle;
        this.triviaQuizDescription = triviaQuizDescription;
        this.numOfQuestions = numOfQuestions;
    }

    public TriviaQuiz() {
        this.triviaQuestions = new ArrayList<>();
    }

    public Long getTriviaQuizId() {
        return triviaQuizId;
    }

    public String getTriviaQuizTitle() {
        return triviaQuizTitle;
    }

    public void setTriviaQuizTitle(String triviaQuizTitle) {
        this.triviaQuizTitle = triviaQuizTitle;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
