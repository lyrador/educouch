package com.educouch.educouchsystem.controller;


import com.educouch.educouchsystem.dto.RoomDTO;
import com.educouch.educouchsystem.dto.RoomTransferObject;
import com.educouch.educouchsystem.dto.SessionInvitationDTO;
import com.educouch.educouchsystem.model.Drawing;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.Room;
import com.educouch.educouchsystem.service.RoomService;
import com.educouch.educouchsystem.util.exception.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController {

    @Autowired
    private RoomService roomsService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomDTO roomDto) {
        Room room = roomsService.initiateRoom(roomDto);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{roomId}/save-drawing")
    public ResponseEntity<String> saveDrawing(@PathVariable String roomId, @RequestBody Drawing drawing) {
        roomsService.saveDrawing(new Long(roomId), drawing);
        return ResponseEntity.ok("Drawing saved successfully!");
    }

    @GetMapping("/{roomId}/get-drawings")
    public ResponseEntity<List<Drawing>> getDrawings(@PathVariable String roomId) {
        List<Drawing> drawings = roomsService.retrieveDrawingByRoomId(new Long(roomId));
        return ResponseEntity.ok(drawings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable String id) {
        try {
            Room room = roomsService.getRoomByRoomId(new Long(id));
            if (room != null) {
                return new ResponseEntity<>(room, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch(RoomNotFoundException ex) {
            System.out.println("Room not found.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/{password}")
    public ResponseEntity<Boolean> checkRoomInvitation(@PathVariable String id, @PathVariable String password) {
        Boolean invitationResult = roomsService.checkRoomInvitation(new Long(id), password);
        if(invitationResult) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<RoomTransferObject> getAllRooms() {

        List<Room> listOfAllRooms =  roomsService.getAllRooms();
        List<RoomTransferObject> modifiedRooms = new ArrayList<>();
        for(Room r: listOfAllRooms) {
            List<String> listOfParticipants = new ArrayList<>();
            List<String> listOfOrganizers = new ArrayList<>();
            for(Learner l: r.getParticipants()) {
                listOfParticipants.add(l.getUsername());
            }
            for(Instructor i: r.getOrganizers()) {
                listOfOrganizers.add(i.getUsername());
            }

            RoomTransferObject modifiedRoom = new RoomTransferObject(r.getRoomId().toString(), r.getName(), r.getPassword(), listOfOrganizers, listOfParticipants);
            modifiedRooms.add(modifiedRoom);
        }
        return modifiedRooms;


    }

    @GetMapping("/{roomId}/get-learner-not-participants")
    public ResponseEntity<List<String>> getLearnerNotParticipants(@PathVariable String roomId) {
        List<String> learnersNotParticipants = roomsService.retrieveAllLearnerNotInCall(new Long(roomId));
        return ResponseEntity.ok(learnersNotParticipants);
    }

    @PostMapping("/sendSessionInvitation")
    public ResponseEntity<String> sendInvitation(@RequestBody SessionInvitationDTO sessionInvitationDTO) {
        List<String> invitedUsernames = sessionInvitationDTO.getInvitedUsername();
        Long roomId = new Long(sessionInvitationDTO.getRoomId());
        roomsService.sendInvitation(invitedUsernames, roomId);
        return ResponseEntity.ok("Invitation email has been successfully sent.");
    }

}
