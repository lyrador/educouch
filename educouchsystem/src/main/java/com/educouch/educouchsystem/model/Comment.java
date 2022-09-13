package com.educouch.educouchsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="forumDiscussion_id")
    private ForumDiscussion forumDiscussion;

    public Comment() {
    }

    public Comment(String content, LocalDateTime timestamp) {
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

    public ForumDiscussion getForumDiscussion() {
        return forumDiscussion;
    }

    public void setForumDiscussion(ForumDiscussion forumDiscussion) {
        this.forumDiscussion = forumDiscussion;
    }
}
