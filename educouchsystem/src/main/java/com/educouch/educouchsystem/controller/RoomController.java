package com.educouch.educouchsystem.controller;


import com.educouch.educouchsystem.dto.RoomDTO;
import com.educouch.educouchsystem.model.Drawing;
import com.educouch.educouchsystem.model.Room;
import com.educouch.educouchsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

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
        Room room = roomsService.getRoomByRoomId(new Long(id));
        if (room != null) {
            return new ResponseEntity<>(room, HttpStatus.OK);
        } else {
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
    public List<Room> getAllRooms() {
        return roomsService.getAllRooms();
    }

}
