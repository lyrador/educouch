package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.EnrolmentStatusTrackerEnum;

import javax.persistence.*;

@Entity
public class EnrolmentStatusTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrolmentStatusTrackerId;

    private EnrolmentStatusTrackerEnum enrolmentStatus;

    @ManyToOne
    @JoinColumn(name = "classRunId")
    private ClassRun classRun;

    @ManyToOne
    @JoinColumn(name = "learnerId")
    private Learner learner;

    public EnrolmentStatusTracker() {
        this.enrolmentStatus = EnrolmentStatusTrackerEnum.DEPOSITPAID;
    }

    public EnrolmentStatusTracker(ClassRun classRun, Learner learner) {
        new EnrolmentStatusTracker();
        this.classRun = classRun;
        this.learner = learner;
    }

    public Long getEnrolmentStatusTrackerId() {
        return enrolmentStatusTrackerId;
    }

    public void setEnrolmentStatusTrackerId(Long enrolmentStatusTrackerId) {
        this.enrolmentStatusTrackerId = enrolmentStatusTrackerId;
    }

    public EnrolmentStatusTrackerEnum getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public void setEnrolmentStatus(EnrolmentStatusTrackerEnum enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }

    public ClassRun getClassRun() {
        return classRun;
    }

    public void setClassRun(ClassRun classRun) {
        this.classRun = classRun;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }
}
