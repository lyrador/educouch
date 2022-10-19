package com.educouch.educouchsystem.dto;

import java.util.List;

public class SessionInvitationDTO {

    private String roomId;
    private List<String> invitedUsername;

    public SessionInvitationDTO(String roomId, List<String> invitedUsername) {
        this.roomId = roomId;
        this.invitedUsername = invitedUsername;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getInvitedUsername() {
        return invitedUsername;
    }

    public void setInvitedUsername(List<String> invitedUsername) {
        this.invitedUsername = invitedUsername;
    }
}
