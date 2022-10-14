package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.data.model.Room;

import java.util.List;

public interface RoomService {

    public Room saveRoom(Room room);

    public List<Room> getAllRooms();

    public Room getRoomByRoomId(Long roomId);

    public void deleteRoom(Long roomId);

    public void addParticipantsToRoom(String learnerUsername, Long roomId);

    public void removeParticipantsFromRoom(String learnerUsername, Long roomId);

}
