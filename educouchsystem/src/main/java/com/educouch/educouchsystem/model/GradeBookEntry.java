package com.educouch.educouchsystem.model;

import javax.persistence.*;

@Entity
public class GradeBookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeBookEntryId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private Long learnerId;

    @Column(nullable = false)
    private String assessmentName;

    @Column(nullable = false)
    private Double assessmentMax;

    private Double learnerScore;

    public GradeBookEntry() {
    }

    public GradeBookEntry(Long courseId, Long learnerId, String assessmentName, Double assessmentMax) {
        this.courseId = courseId;
        this.learnerId = learnerId;
        this.assessmentName = assessmentName;
        this.assessmentMax = assessmentMax;
    }

    public GradeBookEntry(Long courseId,Long learnerId, String assessmentName, Double assessmentMax, Double learnerScore) {
        this.courseId = courseId;
        this.learnerId = learnerId;
        this.assessmentName = assessmentName;
        this.assessmentMax = assessmentMax;
        this.learnerScore = learnerScore;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Long getGradeBookEntryId() {
        return gradeBookEntryId;
    }

    public void setGradeBookEntryId(Long gradeBookEntryId) {
        this.gradeBookEntryId = gradeBookEntryId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Double getAssessmentMax() {
        return assessmentMax;
    }

    public void setAssessmentMax(Double assessmentMax) {
        this.assessmentMax = assessmentMax;
    }

    public Double getLearnerScore() {
        return learnerScore;
    }

    public void setLearnerScore(Double learnerScore) {
        this.learnerScore = learnerScore;
    }
}
