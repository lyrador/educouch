package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.ItemType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private Integer price;
    private String imageUrl;
    private String mediumImageUrl;
    private String largeImageUrl;
    private Boolean smallAvailable = true;
    private Boolean mediumAvailable;
    private Boolean largeAvailable;
    private String itemName;
    private String itemDescription;

    private Integer mediumPointThreshold;
    private Integer largePointThreshold;

    private ItemType itemTypeEnum;

    public Item() {
    }

    public Item(Integer price, String imageUrl, Boolean smallAvailable, Boolean mediumAvailable, Boolean largeAvailable, String itemName, String itemDescription) {
        this.price = price;
        this.imageUrl = imageUrl;
        this.smallAvailable = true;
        this.mediumAvailable = mediumAvailable;
        this.largeAvailable = largeAvailable;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public Item(Integer price, String imageUrl, String mediumImageUrl, String largeImageUrl,
                Boolean mediumAvailable, Boolean largeAvailable, String itemName, String itemDescription,
                Integer mediumPointThreshold, Integer largePointThreshold, ItemType itemTypeEnum) {
        this.price = price;
        this.imageUrl = imageUrl;
        this.mediumImageUrl = mediumImageUrl;
        this.largeImageUrl = largeImageUrl;
        this.mediumAvailable = mediumAvailable;
        this.largeAvailable = largeAvailable;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.mediumPointThreshold = mediumPointThreshold;
        this.largePointThreshold = largePointThreshold;
        this.itemTypeEnum = itemTypeEnum;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getSmallAvailable() {
        return smallAvailable;
    }

    public void setSmallAvailable(Boolean smallAvailable) {
        this.smallAvailable = smallAvailable;
    }

    public Boolean getMediumAvailable() {
        return mediumAvailable;
    }

    public void setMediumAvailable(Boolean mediumAvailable) {
        this.mediumAvailable = mediumAvailable;
    }

    public Boolean getLargeAvailable() {
        return largeAvailable;
    }

    public void setLargeAvailable(Boolean falseAvailable) {
        this.largeAvailable = falseAvailable;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public Integer getMediumPointThreshold() {
        return mediumPointThreshold;
    }

    public void setMediumPointThreshold(Integer mediumPointThreshold) {
        this.mediumPointThreshold = mediumPointThreshold;
    }

    public Integer getLargePointThreshold() {
        return largePointThreshold;
    }

    public void setLargePointThreshold(Integer largePointThreshold) {
        this.largePointThreshold = largePointThreshold;
    }

    public ItemType getItemTypeEnum() {
        return itemTypeEnum;
    }

    public void setItemTypeEnum(ItemType itemTypeEnum) {
        this.itemTypeEnum = itemTypeEnum;
    }
}
