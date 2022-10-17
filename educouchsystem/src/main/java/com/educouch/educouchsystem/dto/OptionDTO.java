package com.educouch.educouchsystem.dto;

public class OptionDTO {

    private Long optionId;

    private String optionContent;

    private String optionIsCorrect;

    public OptionDTO() {
        this.optionContent = "";
    }

    public OptionDTO(String optionContent, String optionIsCorrect, String createdDateTime, Long createdByUserId, String createdByUserName, String createdByUserType) {
        this.optionContent = optionContent;
        this.optionIsCorrect = optionIsCorrect;
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
}
