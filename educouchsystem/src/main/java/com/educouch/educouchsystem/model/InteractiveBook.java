package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class InteractiveBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactiveBookId;
    @Column(name="bookTitle", nullable = false)
    private String bookTitle;
    @Column(name="bookMaxScore", nullable = false)
    private Double bookMaxScore;
    @Column(name="bookActualScore", nullable = false)
    private Double bookActualScore;
    @Column(name="creationDate", nullable = false)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InteractiveChapter> interactiveChapters;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    public InteractiveBook(String bookTitle, Double bookMaxScore, Double bookActualScore, Date creationDate) {
        this.bookTitle = bookTitle;
        this.bookMaxScore = bookMaxScore;
        this.bookActualScore = bookActualScore;
        this.creationDate = creationDate;
    }

    public InteractiveBook() {
    }

    public Long getInteractiveBookId() {
        return interactiveBookId;
    }

    public void setInteractiveBookId(Long interactiveBookId) {
        this.interactiveBookId = interactiveBookId;
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

    public Double getBookActualScore() {
        return bookActualScore;
    }

    public void setBookActualScore(Double bookActualScore) {
        this.bookActualScore = bookActualScore;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<InteractiveChapter> getInteractiveChapters() {
        return interactiveChapters;
    }

    public void setInteractiveChapters(List<InteractiveChapter> interactiveChapters) {
        this.interactiveChapters = interactiveChapters;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
