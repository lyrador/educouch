package com.educouch.educouchsystem.dto;

public class ForumDiscussionDTO {

    private Long forumDiscussionId;

    private String forumDiscussionTitle;

    private String forumDiscussionDescription;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    public ForumDiscussionDTO() {
    }

    public ForumDiscussionDTO(Long forumDiscussionId, String forumDiscussionTitle, String forumDiscussionDescription, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.forumDiscussionId = forumDiscussionId;
        this.forumDiscussionTitle = forumDiscussionTitle;
        this.forumDiscussionDescription = forumDiscussionDescription;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
    }

    public Long getForumDiscussionId() {
        return forumDiscussionId;
    }

    public void setForumDiscussionId(Long forumDiscussionId) {
        this.forumDiscussionId = forumDiscussionId;
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
}
