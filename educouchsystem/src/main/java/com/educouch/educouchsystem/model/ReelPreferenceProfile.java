package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReelPreferenceProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reelPreferenceProfileId;

    @NotNull
    @OneToOne
    private Learner learner;

    @OneToMany
    private List<Course> courseTags;

    public ReelPreferenceProfile() {
        this.courseTags = new ArrayList<>();
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

    public List<Course> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(List<Course> courseTags) {
        this.courseTags = courseTags;
    }
}
