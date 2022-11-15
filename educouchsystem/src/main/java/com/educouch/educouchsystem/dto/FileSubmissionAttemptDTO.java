package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.model.FileSubmission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

public class FileSubmissionAttemptDTO {

    private Long fileSubmissionAttemptId;
    private Double obtainedScore;
    private Attachment attachment;
    private FileSubmissionDTO fileSubmissionAttemptedDTO;

    private String feedback;

    public FileSubmissionAttemptDTO() {
        this.feedback = "";
        this.obtainedScore = 0.0;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    public FileSubmissionDTO getFileSubmissionAttemptedDTO() {
        return fileSubmissionAttemptedDTO;
    }

    public void setFileSubmissionAttemptedDTO(FileSubmissionDTO fileSubmissionAttemptedDTO) {
        this.fileSubmissionAttemptedDTO = fileSubmissionAttemptedDTO;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
