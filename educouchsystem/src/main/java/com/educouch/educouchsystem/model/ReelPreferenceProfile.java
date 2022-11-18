package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ReelPreferenceProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reelPreferenceProfileId;

    @NotNull
    @OneToOne
    private Learner learner;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany
    private Set<Course> courseTags;

    public ReelPreferenceProfile() {
        this.courseTags = new HashSet<>();
    }

    public Long getReelPreferenceProfileId() {
        return reelPreferenceProfileId;
    }

    public void setReelPreferenceProfileId(Long reelPreferenceProfileId) {
        this.reelPreferenceProfileId = reelPreferenceProfileId;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public Set<Course> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(Set<Course> courseTags) {
        this.courseTags = courseTags;
    }
}
