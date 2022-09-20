package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;

    @Column(name="forumTitle", nullable = false, length = 128)
    private String forumTitle;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="forum_id")
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


    public List<ForumDiscussion> getForumDiscussions() {
        return forumDiscussions;
    }

    public void setForumDiscussions(List<ForumDiscussion> forumDiscussions) {
        this.forumDiscussions = forumDiscussions;
    }
}
