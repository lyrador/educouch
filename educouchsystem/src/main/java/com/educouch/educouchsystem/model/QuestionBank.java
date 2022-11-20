package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.service.QuestionBankService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuestionBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionBankId;

    @NotNull
    private String questionBankName;

    @NotNull
    private Long courseId;

    @OneToMany
    private List<Question> questions;

    public QuestionBank() {
        questions = new ArrayList<>();
    }

    public QuestionBank(String questionBankName, Long courseId) {
        this();
        this.questionBankName = questionBankName;
        this.courseId = courseId;
    }

    public String getQuestionBankName() {
        return questionBankName;
    }

    public void setQuestionBankName(String questionBankName) {
        this.questionBankName = questionBankName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
    }
}
