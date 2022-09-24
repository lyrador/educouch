package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.FileSubmissionEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class FileSubmission extends Assessment implements Serializable {

    @Enumerated(EnumType.STRING)
    @NotNull
    private FileSubmissionEnum fileSubmissionEnum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileSubmissionAttempt> fileSubmissionAttempts;

    public FileSubmission() {
        super();
        this.fileSubmissionAttempts = new ArrayList<>();
    }

    public FileSubmission(String title, String description, Double maxScore, Date startDate, Date endDate, FileSubmissionEnum fileSubmissionEnum) {
        super(title, description, maxScore, startDate, endDate);
        this.fileSubmissionEnum = fileSubmissionEnum;
        this.fileSubmissionAttempts = new ArrayList<>();
    }

    public FileSubmissionEnum getFileSubmissionEnum() {
        return fileSubmissionEnum;
    }

    public void setFileSubmissionEnum(FileSubmissionEnum fileSubmissionEnum) {
        this.fileSubmissionEnum = fileSubmissionEnum;
    }

    public List<FileSubmissionAttempt> getFileSubmissionAttempts() {
        return fileSubmissionAttempts;
    }

    public void setFileSubmissionAttempts(List<FileSubmissionAttempt> fileSubmissionAttempts) {
        this.fileSubmissionAttempts = fileSubmissionAttempts;
    }
}
