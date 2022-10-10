package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDTO {

    private Long commentId;

    private String commentTitle;

    private String content;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    private String createdByUserProfilePictureURL;

    private List<CommentDTO> childCommentDTOs;

    private List<Comment> childComments;


    public CommentDTO() {
        this.childCommentDTOs = new ArrayList<>();
        this.childComments = new ArrayList<>();
    }

    public CommentDTO(Long commentId, String commentTitle, String content, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType, String createdByUserProfilePictureURL) {
        this();
        this.commentId = commentId;
        this.commentTitle = commentTitle;
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
        this.createdByUserProfilePictureURL = createdByUserProfilePictureURL;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getCreatedByUserType() {
        return createdByUserType;
    }

    public void setCreatedByUserType(String createdByUserType) {
        this.createdByUserType = createdByUserType;
    }

    public String getCreatedByUserProfilePictureURL() {
        return createdByUserProfilePictureURL;
    }

    public void setCreatedByUserProfilePictureURL(String createdByUserProfilePictureURL) {
        this.createdByUserProfilePictureURL = createdByUserProfilePictureURL;
    }

    public List<CommentDTO> getChildCommentDTOs() {
        return childCommentDTOs;
    }

    public void setChildCommentDTOs(List<CommentDTO> childCommentDTOs) {
        this.childCommentDTOs = childCommentDTOs;
    }

    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }
}
