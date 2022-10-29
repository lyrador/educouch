package com.educouch.educouchsystem.dto;

public class AnnouncementDTO {

    private Long announcementId;

    private String announcementTitle;

    private String announcementBody;

    private String isRead;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    public AnnouncementDTO() {
    }

    public AnnouncementDTO(Long announcementId, String announcementTitle, String announcementBody, String isRead, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementBody = announcementBody;
        this.isRead = isRead;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementBody() {
        return announcementBody;
    }

    public void setAnnouncementBody(String announcementBody) {
        this.announcementBody = announcementBody;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
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
}
