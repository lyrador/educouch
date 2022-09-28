package com.educouch.educouchsystem.dto;

public class OptionDTO {

    private Long optionId;

    private String optionContent;

    private String optionIsCorrect;

    private String createdDateTime;

    private Long createdByUserId;

    private String createdByUserName;

    private String createdByUserType;

    public OptionDTO() {
    }

    public OptionDTO(String optionContent, String optionIsCorrect, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.optionContent = optionContent;
        this.optionIsCorrect = optionIsCorrect;
        this.createdDateTime = createdDateTime;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.createdByUserType = createdByUserType;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionIsCorrect() {
        return optionIsCorrect;
    }

    public void setOptionIsCorrect(String optionIsCorrect) {
        this.optionIsCorrect = optionIsCorrect;
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
