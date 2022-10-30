package com.educouch.educouchsystem.dto;

import javax.persistence.Column;
import java.util.Date;

public class InteractiveBookRequestDTO {
    private String bookTitle;
    private Double bookMaxScore;
    private Long attachmentId;

    public InteractiveBookRequestDTO() {
    }

    public InteractiveBookRequestDTO(String bookTitle, Double bookMaxScore, Long attachmentId) {
        this.bookTitle = bookTitle;
        this.bookMaxScore = bookMaxScore;
        this.attachmentId = attachmentId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Double getBookMaxScore() {
        return bookMaxScore;
    }

    public void setBookMaxScore(Double bookMaxScore) {
        this.bookMaxScore = bookMaxScore;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
