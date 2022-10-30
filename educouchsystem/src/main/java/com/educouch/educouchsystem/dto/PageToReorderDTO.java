package com.educouch.educouchsystem.dto;

public class PageToReorderDTO {
    private Long pageId;
    private String pageNumber;
    private String pageTitle;

    public PageToReorderDTO() {
    }

    public PageToReorderDTO(Long pageId, String pageNumber, String pageTitle) {
        this.pageId = pageId;
        this.pageNumber = pageNumber;
        this.pageTitle = pageTitle;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
}
