package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name="commentTitle", nullable = false, length = 128)
    private String commentTitle;
    @Column(name="content", nullable = false)
    private String content;
    private LocalDateTime timestamp;

    /*@ManyToOne
    @JoinColumn(name="forumDiscussion_id")
    private ForumDiscussion forumDiscussion;*/

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name="forumDiscussion_id")
//    private List<Learner> learners;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name="forumDiscussion_id")
//    private List<Educator> educators;

    @OneToOne
    @JoinColumn(name="learner_id")
    private Learner createdByLearner;

    @OneToOne
    @JoinColumn(name="instructor_id")
    private Instructor createdByInstructor;

    @OneToOne
    @JoinColumn(name="organisationAdmin_id")
    private OrganisationAdmin createdByOrganisationAdmin;

    public Comment() {
    }

    public Comment(String commentTitle, String content, LocalDateTime timestamp) {
        this.commentTitle = commentTitle;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    /*public ForumDiscussion getForumDiscussion() {
        return forumDiscussion;
    }

    public void setForumDiscussion(ForumDiscussion forumDiscussion) {
        this.forumDiscussion = forumDiscussion;
    }*/

    public Learner getCreatedByLearner() {
        return createdByLearner;
    }

    public void setCreatedByLearner(Learner createdByLearner) {
        this.createdByLearner = createdByLearner;
    }

    public Instructor getCreatedByInstructor() {
        return createdByInstructor;
    }

    public void setCreatedByInstructor(Instructor createdByInstructor) {
        this.createdByInstructor = createdByInstructor;
    }

    public OrganisationAdmin getCreatedByOrganisationAdmin() {
        return createdByOrganisationAdmin;
    }

    public void setCreatedByOrganisationAdmin(OrganisationAdmin createdByOrganisationAdmin) {
        this.createdByOrganisationAdmin = createdByOrganisationAdmin;
    }
}
