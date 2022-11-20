package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Learner;

import java.util.List;
import java.util.Set;

public class ReelPreferenceProfileDTO {

    private Long reelPreferenceProfileId;
    private Learner learner;
    private Set<Course> courseTags;

    public ReelPreferenceProfileDTO(Long reelPreferenceProfileId) {
        this.reelPreferenceProfileId = reelPreferenceProfileId;
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
