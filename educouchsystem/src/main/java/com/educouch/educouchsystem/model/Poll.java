package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;

    @Column(name="pollDescription", nullable = false)
    private String pollDescription;

    @Column(name="pollTitle", nullable = false)
    private String pollTitle;

    @Column(name="creationDate", nullable = false)
    private Date creationDate;

    @Column(name="numOfQuestions")
    private Integer numOfQuestions;


    @ManyToOne
    @JoinColumn(name="classRun_id")
    private ClassRun classRun;


    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PollQuestion> pollQuestions;

    public Poll(String pollDescription, String pollTitle, Integer numOfQuestions) {
        this.pollDescription = pollDescription;
        this.pollTitle = pollTitle;
        this.numOfQuestions = numOfQuestions;
    }

    public Poll() {
        this.pollQuestions = new ArrayList<>();
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getPollDescription() {
        return pollDescription;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(Integer numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    @JsonIgnore
    public ClassRun getClassRun() {
        return classRun;
    }

    public void setClassRun(ClassRun classRun) {
        this.classRun = classRun;
    }

    public List<PollQuestion> getPollQuestions() {
        return pollQuestions;
    }

    public void setPollQuestions(List<PollQuestion> pollQuestions) {
        this.pollQuestions = pollQuestions;
    }


}
