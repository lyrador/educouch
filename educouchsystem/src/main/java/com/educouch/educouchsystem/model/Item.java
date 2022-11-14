package com.educouch.educouchsystem.model;

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
    private Boolean smallAvailable;
    private Boolean mediumAvailable;
    private Boolean largeAvailable;
    private String itemName;
    private String itemDescription;

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
}
