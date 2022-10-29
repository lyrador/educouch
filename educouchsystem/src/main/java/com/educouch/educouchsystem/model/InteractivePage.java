package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class InteractivePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactivePageId;

    @Column(name="pageNumber", nullable = false)
    private Integer pageNumber;

    @Column(name="pageDescription", length = 15000)
    private String pageDescription;

    @Column(name="pageTitle")
    private String pageTitle;

    @ManyToOne
    @JoinColumn(name="interactiveChapter_id")
    private InteractiveChapter interactiveChapter;

    @OneToOne
    @JoinColumn(name = "questionId")
    private Question pageQuestion;

    @OneToOne
    @JoinColumn(name = "attachmentId")
    private Attachment attachment;

    public InteractivePage(Integer pageNumber, String pageDescription) {
        this.pageNumber = pageNumber;
        this.pageDescription = pageDescription;
    }

    public InteractivePage(Integer pageNumber, String pageDescription, String pageTitle) {
        this.pageNumber = pageNumber;
        this.pageDescription = pageDescription;
        this.pageTitle = pageTitle;
    }

    public InteractivePage() {
    }

    public Long getInteractivePageId() {
        return interactivePageId;
    }

    public void setInteractivePageId(Long interactivePageId) {
        this.interactivePageId = interactivePageId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    @JsonIgnore
    public InteractiveChapter getInteractiveChapter() {
        return interactiveChapter;
    }

    public void setInteractiveChapter(InteractiveChapter interactiveChapter) {
        this.interactiveChapter = interactiveChapter;
    }

    public Question getPageQuestion() {
        return pageQuestion;
    }

    public void setPageQuestion(Question pageQuestion) {
        this.pageQuestion = pageQuestion;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
}
