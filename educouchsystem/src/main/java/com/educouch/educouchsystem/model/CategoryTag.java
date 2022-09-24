package com.educouch.educouchsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CategoryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryTagId;

    @NotNull
    private String tagName;

    public CategoryTag() {}

    public CategoryTag(String tagName){
        this.tagName = tagName;
    }

    public Long getCategoryTagId() {
        return categoryTagId;
    }

    public void setCategoryTagId(Long categoryTagId) {
        this.categoryTagId = categoryTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
