package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Learner;

import java.util.List;

public class ReelPreferenceProfileDTO {

    private Long reelPreferenceProfileId;
    private Learner learner;
    private List<Course> courseTags;

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

    public List<Course> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(List<Course> courseTags) {
        this.courseTags = courseTags;
    }
}
