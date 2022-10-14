package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.data.model.Instructor;
import com.educouch.educouchsystem.data.model.Learner;
import com.educouch.educouchsystem.data.model.Room;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.repository.LearnerTransactionRepository;
import com.educouch.educouchsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    LearnerService learnerService;

    @Autowired
    EducatorService educatorService;

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

}
