package com.educouch.educouchsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FileSubmissionAttempt implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSubmissionAttemptId;

//    @NotNull
//    private String fileSubmissionAttemptName;

    @NotNull
    private Double obtainedScore = 0.0;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @OneToOne
    @NotNull
    @JoinColumn
    private FileSubmission fileSubmissionAttempted;

    public FileSubmissionAttempt() {
        this.attachments = new ArrayList<>();
    }

//    public FileSubmissionAttempt(String fileSubmissionAttemptName) {
//        this();
//        this.fileSubmissionAttemptName = fileSubmissionAttemptName;
//    }

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
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public FileSubmission getFileSubmissionAttempted() {
        return fileSubmissionAttempted;
    }

    public void setFileSubmissionAttempted(FileSubmission fileSubmission) {
        this.fileSubmissionAttempted = fileSubmission;
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
