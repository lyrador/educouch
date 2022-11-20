package com.educouch.educouchsystem.dto;

public class UpdateLocationDTO {
    private Long itemOwnedId;
    private Long learnerId;
    private Integer x;
    private Integer y;

    public UpdateLocationDTO(Long itemOwnedId, Long learnerId, Integer x, Integer y) {
        this.itemOwnedId = itemOwnedId;
        this.learnerId = learnerId;
        this.x = x;
        this.y = y;
    }

    public UpdateLocationDTO() {
    }

    public Long getItemOwnedId() {
        return itemOwnedId;
    }

    public void setItemOwnedId(Long itemOwnedId) {
        this.itemOwnedId = itemOwnedId;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
