package com.educouch.educouchsystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz extends Assessment implements Serializable {

    @NotNull
    private Boolean hasTimeLimit = Boolean.FALSE;

    @NotNull
    private Boolean isAutoRelease = Boolean.FALSE;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    public Quiz() {
        super();
        this.questions = new ArrayList<>();
    }

    public Quiz(Boolean hasTimeLimit, Boolean isAutoRelease) {
        super();
        this.questions = new ArrayList<>();
        this.hasTimeLimit = hasTimeLimit;
        this.isAutoRelease = isAutoRelease;
    }

    public Boolean getHasTimeLimit() {
        return hasTimeLimit;
    }

    public void setHasTimeLimit(Boolean hasTimeLimit) {
        this.hasTimeLimit = hasTimeLimit;
    }

    public Boolean getAutoRelease() {
        return isAutoRelease;
    }

    public void setAutoRelease(Boolean autoRelease) {
        isAutoRelease = autoRelease;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
