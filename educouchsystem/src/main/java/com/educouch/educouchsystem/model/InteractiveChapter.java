package com.educouch.educouchsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class InteractiveChapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long interactiveChapterId;
    @Column(name="chapterTitle", nullable = false)
    private String chapterTitle;

    @Column(name="chapterIndex", nullable = false)
    private Integer chapterIndex;
    @Column(name="chapterDescription", nullable = false, columnDefinition = "TEXT")
    private String chapterDescription;
    @Column(name="creationDate", nullable = false)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name="interactiveBook_id")
    private InteractiveBook interactiveBook;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InteractivePage> interactivePages;

    public InteractiveChapter(String chapterTitle, String chapterDescription, Date creationDate) {
        this.chapterTitle = chapterTitle;
        this.chapterDescription = chapterDescription;
        this.creationDate = creationDate;
    }

    public InteractiveChapter() {
    }

    public long getInteractiveChapterId() {
        return interactiveChapterId;
    }

    public void setInteractiveChapterId(long interactiveChapterId) {
        this.interactiveChapterId = interactiveChapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public InteractiveBook getInteractiveBook() {
        return interactiveBook;
    }

    public void setInteractiveBook(InteractiveBook interactiveBook) {
        this.interactiveBook = interactiveBook;
    }

    public List<InteractivePage> getInteractivePages() {
        return interactivePages;
    }

    public void setInteractivePages(List<InteractivePage> interactivePages) {
        this.interactivePages = interactivePages;
    }

    public Integer getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(Integer chapterIndex) {
        this.chapterIndex = chapterIndex;
    }
}
