package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.data.model.Drawing;
import com.educouch.educouchsystem.model.Educator;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.data.model.Room;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.DrawingRepository;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.repository.LearnerTransactionRepository;
import com.educouch.educouchsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    LearnerService learnerService;

    @Autowired
    EducatorService educatorService;

    @Autowired
    DrawingRepository drawingRepository;

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    @Override
    public Room getRoomByRoomId(Long roomId) {
        return roomRepository.findById(roomId).get();
    }
    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }
    @Override
    public void addParticipantsToRoom(String username, Long roomId) {
        Room room = getRoomByRoomId(roomId);
        Learner learner = learnerService.findLearnerByUsername(username);
        if(learner != null) {
            room.getParticipants().add(learner);
        } else {
            Instructor instructor = educatorService.findInstructorByUsername(username);
            room.getOrganizers().add(instructor);

        }


        this.saveRoom(room);
    }


    @Override
    public void removeParticipantsFromRoom(String username, Long roomId) {
        Room room = getRoomByRoomId(roomId);
        Learner learner = learnerService.findLearnerByUsername(username);

        if(learner != null) {
            room.getParticipants().remove(learner);
        } else {
            Instructor instructor = educatorService.findInstructorByUsername(username);
            room.getOrganizers().remove(instructor);
        }

        room = saveRoom(room);

        // if room is empty, remove from database
        if(room.getParticipants().size() == 0 && room.getOrganizers().size() == 0) {
            deleteRoom(room.getRoomId());
        }


    }

    public List<String> retrieveListOfUsers(Long roomId) {
        Room room = getRoomByRoomId(roomId);
        List<String> usernames = new ArrayList<>();
        List<Instructor> instructors = room.getOrganizers();
        List<Learner> participants = room.getParticipants();

        for(Instructor instructor: instructors) {
            usernames.add(instructor.getUsername());

        }

        for(Learner learner: participants) {
            usernames.add(learner.getUsername());
        }

        return usernames;
    }

    public void saveDrawing(Long roomId, Drawing drawing) {
        Room room = getRoomByRoomId(roomId);
        drawing = drawingRepository.save(drawing);

        room.getDrawings().add(drawing);
        roomRepository.save(room);

    }
    public List<Drawing> retrieveDrawingByRoomId(Long roomId) {
        Room room = getRoomByRoomId(roomId);
        return room.getDrawings();

    }

}
