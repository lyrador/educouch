package com.educouch.educouchsystem.model;

import com.educouch.educouchsystem.util.enumeration.ItemSizeEnum;

import javax.persistence.*;

@Entity
public class ItemOwned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemOwnedId;
    private Integer positionX;
    private Integer positionY;
    private Boolean isHorizontal = false;
    private Boolean isHidden = false;
    private ItemSizeEnum size = ItemSizeEnum.SMALL;

    private Integer itemPoints = 0;

    private String currImageUrl;

    @ManyToOne
    @JoinColumn
    private Item item;

    public ItemOwned() {
    }

    public ItemOwned(Integer positionX, Integer positionY, Boolean isHorizontal, ItemSizeEnum size, Item item) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isHorizontal = isHorizontal;
        this.size = size;
        this.item = item;
    }

    public ItemOwned(Integer positionX, Integer positionY, Boolean isHorizontal, ItemSizeEnum size) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isHorizontal = isHorizontal;
        this.size = size;
    }

    public ItemOwned(Integer positionX, Integer positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

    }

    public Long getItemOwnedId() {
        return itemOwnedId;
    }

    public void setItemOwnedId(Long itemOwnedId) {
        this.itemOwnedId = itemOwnedId;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Boolean getHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(Boolean horizontal) {
        isHorizontal = horizontal;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public ItemSizeEnum getSize() {
        return size;
    }

    public void setSize(ItemSizeEnum size) {
        this.size = size;
    }

    public Item getItem() {
        return item;
    }


    // set item is only called once!
    public void setItem(Item item) {
        this.item = item;
        this.currImageUrl = this.item.getImageUrl();
    }

    public Integer getItemPoints() {
        return itemPoints;
    }

    public void setItemPoints(Integer itemPoints) {
        this.itemPoints = itemPoints;
    }

    public String getCurrImageUrl() {
        return currImageUrl;
    }

    public void setCurrImageUrl(String currImageUrl) {
        this.currImageUrl = currImageUrl;
    }
}
