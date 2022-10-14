package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.data.model.Drawing;
import com.educouch.educouchsystem.data.model.Room;

import java.util.List;

public interface RoomService {

    public Room saveRoom(Room room);

    public List<Room> getAllRooms();

    public Room getRoomByRoomId(Long roomId);

    public void deleteRoom(Long roomId);

    public void addParticipantsToRoom(String learnerUsername, Long roomId);

    public void removeParticipantsFromRoom(String learnerUsername, Long roomId);

    public List<String> retrieveListOfUsers(Long roomId);

    public void saveDrawing(Long roomId, Drawing drawing);

    public List<Drawing> retrieveDrawingByRoomId(Long roomId);

}
