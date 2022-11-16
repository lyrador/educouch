package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class FileSubmissionAttempt implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSubmissionAttemptId;

//    @NotNull
//    private String fileSubmissionAttemptName;

    private String feedback;

    @NotNull
    private Double obtainedScore = 0.0;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAttemptTime;

    @OneToOne(cascade = {CascadeType.REMOVE})
    private Attachment attachments;

    @OneToOne
    @NotNull
    @JoinColumn
    private FileSubmission fileSubmissionAttempted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Learner learner;

    @NotNull
    private AssessmentAttemptStatusEnum assessmentAttemptStatusEnum;

    public FileSubmissionAttempt() {
        this.lastAttemptTime = new Date();
        this.assessmentAttemptStatusEnum = AssessmentAttemptStatusEnum.SUBMITTED;
        this.feedback = "";


    }

//    public FileSubmissionAttempt(String fileSubmissionAttemptName) {
//        this();
//        this.fileSubmissionAttemptName = fileSubmissionAttemptName;
//    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public AssessmentAttemptStatusEnum getAssessmentAttemptStatusEnum() {
        return assessmentAttemptStatusEnum;
    }

    public void setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum assessmentAttemptStatusEnum) {
        this.assessmentAttemptStatusEnum = assessmentAttemptStatusEnum;
    }

    public Date getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(Date lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    public Long getFileSubmissionAttemptId() {
        return fileSubmissionAttemptId;
    }

    public void setFileSubmissionAttemptId(Long fileSubmissionAttemptId) {
        this.fileSubmissionAttemptId = fileSubmissionAttemptId;
    }

    public Double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(Double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public Attachment getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachment attachments) {
        this.attachments = attachments;
    }

    public FileSubmission getFileSubmissionAttempted() {
        return fileSubmissionAttempted;
    }

    public void setFileSubmissionAttempted(FileSubmission fileSubmission) {
        this.fileSubmissionAttempted = fileSubmission;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileSubmissionAttemptId != null ? fileSubmissionAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof FileSubmissionAttempt)) {
            return false;
        }
        FileSubmissionAttempt other = (FileSubmissionAttempt) object;
        if ((this.fileSubmissionAttemptId == null && other.fileSubmissionAttemptId != null) || (this.fileSubmissionAttemptId != null && !this.fileSubmissionAttemptId.equals(other.fileSubmissionAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FileSubmissionAttempt[ id=" + fileSubmissionAttemptId + " ]";
    }
}
