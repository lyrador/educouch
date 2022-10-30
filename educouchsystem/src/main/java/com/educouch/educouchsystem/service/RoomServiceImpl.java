package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.dto.RoomDTO;
import com.educouch.educouchsystem.model.Drawing;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.Room;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.repository.DrawingRepository;
import com.educouch.educouchsystem.repository.RoomRepository;
import com.educouch.educouchsystem.util.exception.RoomNotFoundException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//        newRoom.addOrganizer(instructor);
        return roomRepository.save(newRoom);
    }
    @Override
    public List<Room> getAllRooms() {
        System.out.println("Called b");
        return roomRepository.findAll();
    }
    @Override
    public Room getRoomByRoomId(Long roomId) throws RoomNotFoundException{
        Room r = roomRepository.findById(roomId).get();
        if(r == null) {
            throw new RoomNotFoundException("Room doesn't exist.");
        } else {
            return r;
        }

    }
    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }
    @Override
    public void addParticipantsToRoom(String username, Long roomId) {
        try {
            Room room = getRoomByRoomId(roomId);
            System.out.println("Attempt to add someone to the call.");
            Boolean containsLearner = false;
            for (Learner l : room.getParticipants()) {
                if (l.getUsername().equals(username)) {
                    containsLearner = true;
                }
            }
            try {
                Learner learner = learnerService.findLearnerByUsername(username);
                if (!containsLearner) {
                    System.out.println("Adding learner in." + learner.getUsername());
                    room.getParticipants().add(learner);
                } else {
                    System.out.println("Learner exists!");
                }

            } catch (UsernameNotFoundException ex) {
                Boolean containsOrganizer = false;
                for (Instructor i : room.getOrganizers()) {
                    if (i.getUsername().equals(username)) {
                        containsOrganizer = true;
                    }
                }
                Instructor instructor = educatorService.findInstructorByUsername(username);
                if (!containsOrganizer) {
                    System.out.println("Adding instructor in." + instructor.getUsername());
                    room.getOrganizers().add(instructor);
                } else {
                    System.out.println("Organizer exists!");
                }

            }
            this.saveRoom(room);
        } catch(RoomNotFoundException ex) {
            System.out.println("Room not found exception.");
        }
    }


    @Override
    public void removeParticipantsFromRoom(String username, Long roomId) {
        try {
            List<Learner> remainingParticipants = new ArrayList<>();
            List<Instructor> remainingOrganizers = new ArrayList<>();
            Room room = getRoomByRoomId(roomId);
            System.out.println("Attempting to disconnect participant.");
            try {
                Learner learner = learnerService.findLearnerByUsername(username);
                remainingParticipants = room.getParticipants().stream()
                        .filter(x -> !x.getUsername().equals(username))
                        .collect(Collectors.toList());

                room.setParticipants(remainingParticipants);
                System.out.println("Disconnect learner: " + learner.getUsername());

            } catch (UsernameNotFoundException ex) {
                Instructor instructor = educatorService.findInstructorByUsername(username);
                remainingOrganizers = room.getOrganizers().stream()
                        .filter(x -> !x.getUsername().equals(username))
                        .collect(Collectors.toList());

                room.setOrganizers(remainingOrganizers);
                System.out.println("Disconnect instructor: " + instructor.getUsername());

            }

            this.saveRoom(room);

            room = getRoomByRoomId(roomId);
            if (room.getParticipants().size() == 0 && room.getOrganizers().size() == 0) {
                this.deleteRoom(room.getRoomId());
                System.out.println("Room is deleted.");
            }
        } catch(RoomNotFoundException ex) {
            System.out.println("Room not found exception.");
        }




    }

    public List<String> retrieveListOfUsers(Long roomId) {
        try {
            Room room = getRoomByRoomId(roomId);
            List<String> usernames = new ArrayList<>();
            List<Instructor> instructors = room.getOrganizers();
            List<Learner> participants = room.getParticipants();

            for (Instructor instructor : instructors) {
                usernames.add(instructor.getUsername());

            }

            for (Learner learner : participants) {
                usernames.add(learner.getUsername());
            }

            return usernames;
        } catch(RoomNotFoundException ex) {
            System.out.println("Room cannot be found.");
            return new ArrayList<>();
        }
    }

    public void saveDrawing(Long roomId, Drawing drawing) {
        try {
            Room room = getRoomByRoomId(roomId);
            drawing = drawingRepository.save(drawing);

            room.getDrawings().add(drawing);
            roomRepository.save(room);
        } catch(RoomNotFoundException ex) {
            System.out.println("Room cannot be found.");
        }
    }
    public List<Drawing> retrieveDrawingByRoomId(Long roomId) {
        try {
            Room room = getRoomByRoomId(roomId);
            return room.getDrawings();
        } catch(RoomNotFoundException ex) {
            System.out.println("Room cannot be found.");
            return new ArrayList<>();
        }

    }

    @Override
    public Boolean checkRoomInvitation(Long roomId, String password) {
        try {
            Room room = getRoomByRoomId(roomId);
            if (room.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch(RoomNotFoundException ex) {
            System.out.println("Room cannot be found.");
            return null;
        }
    }

    public List<String> retrieveAllLearnerNotInCall(Long roomId) {
        try {
            Room room = getRoomByRoomId(roomId);
            List<Learner> listOfParticipants = room.getParticipants();
            List<Learner> listOfLearners = learnerService.getAllLearners();

            List<String> notParticipantYet = new ArrayList<>();
            for (Learner l : listOfLearners) {
                if (!listOfParticipants.contains(l)) {
                    notParticipantYet.add(l.getUsername());
                }
            }

            return notParticipantYet;
        } catch(RoomNotFoundException ex) {
            System.out.println("Room cannot be found.");
            return null;
        }
    }

    public void sendInvitation(List<String> invited, Long roomId) {
        try {
            Room room = getRoomByRoomId(roomId);
            String password = room.getPassword();

            String message = "You are invited to join a meeting. Details are as follow:\nRoom ID: " + roomId.toString() + "\n" + "Passcode: " + password;

            for (String username : invited) {
                Learner learner = learnerService.findLearnerByUsername(username);
                String email = learner.getEmail();
                String personalJoinLink = "http://localhost:3000/room/" + roomId.toString() + "?username=" + username;
                if (email != null) {
                    emailSenderService.sendEmail(email, "Invitation to join a whiteboard session", message + "\nWhiteboard session link: " + personalJoinLink);

                }
            }
        } catch(RoomNotFoundException ex) {
            System.out.println("Room cannot be found.");
        }


    }

}
