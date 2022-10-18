package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.dto.RoomDTO;
import com.educouch.educouchsystem.model.Drawing;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.Room;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.repository.DrawingRepository;
import com.educouch.educouchsystem.repository.RoomRepository;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room initiateRoom(RoomDTO roomDto) {
        Room newRoom = new Room(roomDto.getRoomName(), "");
        Instructor instructor = educatorService.findInstructorByUsername(roomDto.getCreatorUsername());
        // when first start, the room will have no participants
        newRoom.addOrganizer(instructor);
        return roomRepository.save(newRoom);
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
        try {
            Learner learner = learnerService.findLearnerByUsername(username);
            room.getParticipants().add(learner);
        } catch(UsernameNotFoundException ex) {
            Instructor instructor = educatorService.findInstructorByUsername(username);
            room.getOrganizers().add(instructor);
        }
        this.saveRoom(room);
    }


    @Override
    public void removeParticipantsFromRoom(String username, Long roomId) {
        Room room = getRoomByRoomId(roomId);

        try {
            Learner learner = learnerService.findLearnerByUsername(username);
            room.getParticipants().remove(learner);
        } catch(UsernameNotFoundException ex) {
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

    @Override
    public Boolean checkRoomInvitation(Long roomId, String password) {
        Room room = getRoomByRoomId(roomId);
        if(room.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> retrieveAllLearnerNotInCall(Long roomId) {
        Room room = getRoomByRoomId(roomId);
        List<Learner> listOfParticipants = room.getParticipants();
        List<Learner> listOfLearners = learnerService.getAllLearners();

        List<String> notParticipantYet = new ArrayList<>();
        for(Learner l: listOfLearners) {
            if(!listOfParticipants.contains(l)) {
                notParticipantYet.add(l.getUsername());
            }
        }

        return notParticipantYet;
    }

    public void sendInvitation(List<String> invited, Long roomId) {
        Room room = getRoomByRoomId(roomId);
        String password = room.getPassword();

        String message = "You are invited to join a meeting. Details are as follow:\nRoom ID: " + roomId.toString() +"\n" + "Passcode: " + password;

        for(String username: invited) {
            Learner learner = learnerService.findLearnerByUsername(username);
            String email = learner.getEmail();
            String personalJoinLink = "http://localhost:3000/room/13?username=" + username;
            if(email!= null) {
                emailSenderService.sendEmail(email, "Invitation to join a whiteboard session", message + "\nWhiteboard session link: " + personalJoinLink);

            }
        }


    }

}
