package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ForumDiscussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumDiscussionId;

    @Column(name="forumDiscussionTitle", nullable = false, length = 128)
    private String forumDiscussionTitle;

    @Column(name="forumDiscussionDescription", nullable = false)
    private String forumDiscussionDescription;
    private LocalDateTime timestamp;

    /*@ManyToOne
    @JoinColumn(name="forum_id")
    private Forum forum;*/

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="forumDiscussion_id")
    private List<Comment> comments;

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

    public ForumDiscussion() {
    }

    public ForumDiscussion(String forumDiscussionTitle, String forumDiscussionDescription, LocalDateTime timestamp) {
        this.forumDiscussionTitle = forumDiscussionTitle;
        this.forumDiscussionDescription = forumDiscussionDescription;
        this.timestamp = timestamp;
    }

    public Long getForumDiscussionId() {
        return forumDiscussionId;
    }

    public void setForumDiscussionId(Long forumDiscussionId) {
        this.forumDiscussionId = forumDiscussionId;
    }

    public String getForumDiscussionTitle() {
        return forumDiscussionTitle;
    }

    public void setForumDiscussionTitle(String forumDiscussionTitle) {
        this.forumDiscussionTitle = forumDiscussionTitle;
    }

    public String getForumDiscussionDescription() {
        return forumDiscussionDescription;
    }

    public void setForumDiscussionDescription(String forumDiscussionDescription) {
        this.forumDiscussionDescription = forumDiscussionDescription;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

  /*public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }*/

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

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

