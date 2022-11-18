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
    private ItemSizeEnum size;

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

    public void setItem(Item item) {
        this.item = item;
    }
}
