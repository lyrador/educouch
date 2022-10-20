package com.educouch.educouchsystem.model;

import javax.persistence.*;

@Entity
public class TextItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long textItemId;

    @Column(nullable = false, name = "textItemWords")
    private String textItemWords;

    @Column(nullable = false, name = "textFontSize")
    private Double textFontSize;

    @OneToOne(mappedBy = "textItem")
    private PageItem pageItem;

    public TextItem(String textItemWords) {
        this.textItemWords = textItemWords;
    }

    public TextItem() {
    }

    public Long getTextItemId() {
        return textItemId;
    }

    public void setTextItemId(Long textItemId) {
        this.textItemId = textItemId;
    }

    public String getTextItemWords() {
        return textItemWords;
    }

    public void setTextItemWords(String textItemWords) {
        this.textItemWords = textItemWords;
    }

    public PageItem getPageItem() {
        return pageItem;
    }

    public void setPageItem(PageItem pageItem) {
        this.pageItem = pageItem;
    }

    public Double getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(Double textFontSize) {
        this.textFontSize = textFontSize;
    }
}
