package com.educouch.educouchsystem.dto;

import java.util.List;

public class RoomTransferObject {
    private String roomId;
    private String password;
    private String roomName;
    private List<String> organizers;
    private List<String> participants;

    public RoomTransferObject(String roomId, String roomName,String password, List<String> organizers, List<String> participants) {
        this.roomId = roomId;
        this.password = password;
        this.roomName = roomName;
        this.organizers = organizers;
        this.participants = participants;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<String> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<String> organizers) {
        this.organizers = organizers;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
