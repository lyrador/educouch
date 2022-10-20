package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class PageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageItemId;

    @Column(name="pageItemXPosition", nullable = false)
    private Double pageItemXPosition;

    @Column(name="pageItemYPosition", nullable = false)
    private Double pageItemYPosition;

    @ManyToOne
    @JoinColumn(name="interactivePage_id")
    private InteractivePage interactivePage;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @OneToOne
    @JoinColumn(name = "textItem_id")
    private TextItem textItem;

    public PageItem(Double pageItemXPosition, Double pageItemYPosition) {
        this.pageItemXPosition = pageItemXPosition;
        this.pageItemYPosition = pageItemYPosition;
    }

    public PageItem() {
    }

    public Long getPageItemId() {
        return pageItemId;
    }

    public void setPageItemId(Long pageItemId) {
        this.pageItemId = pageItemId;
    }

    public Double getPageItemXPosition() {
        return pageItemXPosition;
    }

    public void setPageItemXPosition(Double pageItemXPosition) {
        this.pageItemXPosition = pageItemXPosition;
    }

    public Double getPageItemYPosition() {
        return pageItemYPosition;
    }

    public void setPageItemYPosition(Double pageItemYPosition) {
        this.pageItemYPosition = pageItemYPosition;
    }

    @JsonIgnore
    public InteractivePage getInteractivePage() {
        return interactivePage;
    }

    public void setInteractivePage(InteractivePage interactivePage) {
        this.interactivePage = interactivePage;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public TextItem getTextItem() {
        return textItem;
    }

    public void setTextItem(TextItem textItem) {
        this.textItem = textItem;
    }
}
