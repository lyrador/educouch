package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DocumentSubmissionAttempt implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentSubmissionAttemptId;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private DocumentSubmission documentSubmission;


    public DocumentSubmissionAttempt() {
    }

    public DocumentSubmissionAttempt(DocumentSubmission documentSubmission) {
        this.documentSubmission = documentSubmission;
    }

    public Long getDocumentSubmissionAttemptId() {
        return documentSubmissionAttemptId;
    }

    public void setDocumentSubmissionAttemptId(Long documentSubmissionAttemptId) {
        this.documentSubmissionAttemptId = documentSubmissionAttemptId;
    }

    public DocumentSubmission getDocumentSubmission() {
        return documentSubmission;
    }

    public void setDocumentSubmission(DocumentSubmission documentSubmission) {
        this.documentSubmission = documentSubmission;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentSubmissionAttemptId != null ? documentSubmissionAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof DocumentSubmissionAttempt)) {
            return false;
        }
        DocumentSubmissionAttempt other = (DocumentSubmissionAttempt) object;
        if ((this.documentSubmissionAttemptId == null && other.documentSubmissionAttemptId != null) || (this.documentSubmissionAttemptId != null && !this.documentSubmissionAttemptId.equals(other.documentSubmissionAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentSubmissionAttempt[ id=" + documentSubmissionAttemptId + " ]";
    }

}
