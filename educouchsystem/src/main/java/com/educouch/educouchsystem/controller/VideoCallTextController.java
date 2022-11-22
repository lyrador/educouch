package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ActionResponseDTO;
import com.educouch.educouchsystem.dto.RoomActionsDTO;
import com.educouch.educouchsystem.dto.TextMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class VideoCallTextController {

    @Autowired
    SimpMessagingTemplate template;


    @MessageMapping("/send/{userId}/chat")
    @SendTo("/topic/{userId}/chat")
    public TextMessage sendChat(@Payload TextMessage textMessage) {
        return textMessage;
    }



}
