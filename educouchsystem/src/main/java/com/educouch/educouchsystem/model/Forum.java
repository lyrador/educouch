package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;
    private String forumTitle;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany
    @JoinColumn(name="forum")
    private List<ForumDiscussion> forumDiscussions;

    public Forum() {
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ForumDiscussion> getForumDiscussions() {
        return forumDiscussions;
    }

    public void setForumDiscussions(List<ForumDiscussion> forumDiscussions) {
        this.forumDiscussions = forumDiscussions;
    }
}
