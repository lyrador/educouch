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

@RestController
//giving learner path here
@RequestMapping("/whiteboard")
//tells the react and springboot application to connect to each other
@CrossOrigin(origins = "*", maxAge = 3600)
public class WebSocketTextController {

    @Autowired
    RoomService roomService;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired


    @MessageMapping("/send/{roomId}")
    @SendTo("/topic/{roomId}")
    public Coordinates sendMessage(@Payload Coordinates coordinates) {
        return coordinates;
    }

//    public ActionResponseDTO joinUser(@Payload RoomActionsDTO roomActions) {
//        String username = roomActions.getUsername();
//        String action = roomActions.getPayload();
//        String roomId = roomActions.getRoomId();
//    }

}
