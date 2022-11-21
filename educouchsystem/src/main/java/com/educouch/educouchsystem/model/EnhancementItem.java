package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.ItemType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EnhancementItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enhancementItemId;

    private Integer pricePerUse;

    private String imageUrl;

    private String enhancementItemName;

    private String enhancementItemDescription;

    private Integer itemPointIncrement;

    private ItemType itemType;

    public EnhancementItem() {
    }

    public EnhancementItem(Integer pricePerUse, String imageUrl, String enhancementItemName,
                           String enhancementItemDescription, Integer itemPointIncrement, ItemType itemType) {
        this.pricePerUse = pricePerUse;
        this.imageUrl = imageUrl;
        this.enhancementItemName = enhancementItemName;
        this.enhancementItemDescription = enhancementItemDescription;
        this.itemPointIncrement = itemPointIncrement;
        this.itemType = itemType;
    }

    public Long getEnhancementItemId() {
        return enhancementItemId;
    }

    public void setEnhancementItemId(Long enhancementItemId) {
        this.enhancementItemId = enhancementItemId;
    }

    public Integer getPricePerUse() {
        return pricePerUse;
    }

    public void setPricePerUse(Integer pricePerUse) {
        this.pricePerUse = pricePerUse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEnhancementItemName() {
        return enhancementItemName;
    }

    public void setEnhancementItemName(String enhancementItemName) {
        this.enhancementItemName = enhancementItemName;
    }

    public String getEnhancementItemDescription() {
        return enhancementItemDescription;
    }

    public void setEnhancementItemDescription(String enhancementItemDescription) {
        this.enhancementItemDescription = enhancementItemDescription;
    }

    public Integer getItemPointIncrement() {
        return itemPointIncrement;
    }

    public void setItemPointIncrement(Integer itemPointIncrement) {
        this.itemPointIncrement = itemPointIncrement;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
