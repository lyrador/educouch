package com.educouch.educouchsystem.controller;


import com.educouch.educouchsystem.data.model.Coordinates;
import com.educouch.educouchsystem.dto.ActionResponseDTO;
import com.educouch.educouchsystem.dto.RoomActionsDTO;
import com.educouch.educouchsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class WebSocketTextController {

    @Autowired
    RoomService roomService;

    @Autowired
    SimpMessagingTemplate template;


    @MessageMapping("/send/{roomId}")
    @SendTo("/topic/{roomId}")
    public Coordinates sendMessage(@Payload Coordinates coordinates) {
        return coordinates;
    }

    @MessageMapping("/send/{roomId}/user")
    @SendTo("/topic/{roomId}/user")
    public ActionResponseDTO joinUser(@Payload RoomActionsDTO roomActions) {
        String username = roomActions.getUsername();
        String action = roomActions.getPayload();
        String roomId = roomActions.getRoomId();
        String message = "";
        if(action.equals("CONNECT_USER")) {
            message = "User " + username + " has successfully connected!";
            roomService.addParticipantsToRoom(username, new Long(roomId));
            List<String> usernames = roomService.retrieveListOfUsers(new Long(roomId));
            return new ActionResponseDTO(message, usernames);

        } else if(action.equals("DISCONNECT_USER")) {
            message = "User " + username + " has disconnected!";
            roomService.removeParticipantsFromRoom(username, new Long(roomId));
            List<String> usernames = roomService.retrieveListOfUsers(new Long(roomId));
            return new ActionResponseDTO(message, usernames);
        } else {
            return new ActionResponseDTO("Not supported", new ArrayList<String>());
        }


    }

}
