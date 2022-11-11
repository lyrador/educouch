package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.RoomDTO;
import com.educouch.educouchsystem.model.Drawing;
import com.educouch.educouchsystem.model.Room;
import com.educouch.educouchsystem.util.exception.RoomNotFoundException;

import java.util.List;

public interface RoomService {

    public Room saveRoom(Room room);

    public List<Room> getAllRooms();

    public Room getRoomByRoomId(Long roomId) throws RoomNotFoundException;

    public void deleteRoom(Long roomId);

    public void addParticipantsToRoom(String learnerUsername, Long roomId);

    public void removeParticipantsFromRoom(String learnerUsername, Long roomId);

    public List<String> retrieveListOfUsers(Long roomId);

    public void saveDrawing(Long roomId, Drawing drawing);

    public List<Drawing> retrieveDrawingByRoomId(Long roomId);

    public Room initiateRoom(RoomDTO roomDto);

    public Boolean checkRoomInvitation(Long roomId, String password);

    public List<String> retrieveAllLearnerNotInCall(Long roomId);

    public void sendInvitation(List<String> invited, Long roomId);

}
