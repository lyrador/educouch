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
    private Long assessmentId;

    @Column(nullable = false)
    private String assessmentName;

    @Column(nullable = false)
    private Double assessmentMax;
@Column(nullable = false)
    private Double learnerScore;

    @Column(nullable = false)
    private boolean isPublished;

    public GradeBookEntry() {
        isPublished = false;
        learnerScore = 0.0;
    }

    public GradeBookEntry(Long courseId, Long learnerId,Long assessmentId, String assessmentName, Double assessmentMax) {
        this();
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.learnerId = learnerId;
        this.assessmentName = assessmentName;
        this.assessmentMax = assessmentMax;

    }

    public GradeBookEntry(Long courseId,Long learnerId, Long assessmentId, String assessmentName, Double assessmentMax, Double learnerScore) {
        this();
        this.courseId = courseId;
        this.learnerId = learnerId;
        this.assessmentId = assessmentId;

        this.assessmentName = assessmentName;
        this.assessmentMax = assessmentMax;
        this.learnerScore = learnerScore;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
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
