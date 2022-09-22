package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;

    @Column(name="forumTitle", nullable = false, length = 128)
    private String forumTitle;

    private LocalDateTime timestamp;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="forum_id")
    private List<ForumDiscussion> forumDiscussions;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="forumDiscussion_id")
    private List<Learner> learners;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="forumDiscussion_id")
    private List<Educator> educators;

    public Forum() {
    }

    public Forum(String forumTitle, LocalDateTime timestamp) {
        this.forumTitle = forumTitle;
        this.timestamp = timestamp;
    }

    public Forum(String forumTitle) {
        this.forumTitle = forumTitle;
    }

    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public String getForumTitle() {
        return forumTitle;
    }

    public void setForumTitle(String forumTitle) {
        this.forumTitle = forumTitle;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<ForumDiscussion> getForumDiscussions() {
        return forumDiscussions;
    }

    public void setForumDiscussions(List<ForumDiscussion> forumDiscussions) {
        this.forumDiscussions = forumDiscussions;
    }
    @JsonIgnore
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
