package com.educouch.educouchsystem.dto;

import java.util.List;

public class RoomDTO {
    private String creatorUsername;

    private String roomName;
    private String description;
    private List<String> participants;

    public RoomDTO(String creatorUsername, String roomName, String description, List<String> participants) {
        this.creatorUsername = creatorUsername;
        this.roomName = roomName;
        this.description = description;
        this.participants = participants;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
