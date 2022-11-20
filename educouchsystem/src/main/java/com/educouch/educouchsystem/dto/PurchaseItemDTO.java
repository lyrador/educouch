package com.educouch.educouchsystem.dto;

import com.educouch.educouchsystem.model.ItemOwned;

public class PurchaseItemDTO {
    public String learnerId;
    public String itemId;
    public Integer positionX;
    public Integer positionY;

    public PurchaseItemDTO() {
    }

    public PurchaseItemDTO(String learnerId, String itemId, Integer positionX, Integer positionY) {
        this.learnerId = learnerId;
        this.itemId = itemId;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
