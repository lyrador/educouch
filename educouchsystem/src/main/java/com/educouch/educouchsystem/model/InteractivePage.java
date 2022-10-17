package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class InteractivePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactivePageId;

    @Column(name="pageNumber", nullable = false)
    private Integer pageNumber;

    @Column(name="pageDescription", nullable = false, columnDefinition = "TEXT")
    private String pageDescription;

    @ManyToOne
    @JoinColumn(name="interactiveChapter_id")
    private InteractiveChapter interactiveChapter;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Question> pageQuestions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Attachment> attachments;

    public InteractivePage(Integer pageNumber, String pageDescription) {
        this.pageNumber = pageNumber;
        this.pageDescription = pageDescription;
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

    public InteractiveChapter getInteractiveChapter() {
        return interactiveChapter;
    }

    public void setInteractiveChapter(InteractiveChapter interactiveChapter) {
        this.interactiveChapter = interactiveChapter;
    }

    public List<Question> getPageQuestions() {
        return pageQuestions;
    }

    public void setPageQuestions(List<Question> pageQuestions) {
        this.pageQuestions = pageQuestions;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
