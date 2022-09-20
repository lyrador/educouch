package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.DocumentSubmissionEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DocumentSubmission extends Assessment implements Serializable {

    @Enumerated(EnumType.STRING)
    @NotNull
    private DocumentSubmissionEnum documentSubmission;

    @OneToMany(mappedBy = "documentSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentSubmissionAttempt> documentSubmissionAttempts;

    public DocumentSubmission() {
        super();
        this.documentSubmissionAttempts = new ArrayList<>();
    }

    public DocumentSubmission(DocumentSubmissionEnum documentSubmission) {
        super();
        this.documentSubmission = documentSubmission;
        this.documentSubmissionAttempts = new ArrayList<>();
    }

    public DocumentSubmissionEnum getDocumentSubmission() {
        return documentSubmission;
    }

    public void setDocumentSubmission(DocumentSubmissionEnum documentSubmission) {
        this.documentSubmission = documentSubmission;
    }

    public List<DocumentSubmissionAttempt> getDocumentSubmissionAttempts() {
        return documentSubmissionAttempts;
    }

    public void setDocumentSubmissionAttempts(List<DocumentSubmissionAttempt> documentSubmissionAttempts) {
        this.documentSubmissionAttempts = documentSubmissionAttempts;
    }
}
