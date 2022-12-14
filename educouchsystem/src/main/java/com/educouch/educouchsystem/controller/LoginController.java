package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.LoggedInUserDTO;
import com.educouch.educouchsystem.dto.LoggedInUserRequestDTO;
import com.educouch.educouchsystem.model.Comment;
import com.educouch.educouchsystem.model.ForumDiscussion;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.ResponseData;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.LoginService;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    private AttachmentService attachmentService;

    private LoginService loginService;

    @Autowired
    private LearnerService learnerService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedInUserDTO> login(@RequestBody LoggedInUserRequestDTO loggedInUserRequestDTO) throws Exception {
        try {
            LoggedInUserDTO loggedInUserDTO = loginService.login(loggedInUserRequestDTO);
            return new ResponseEntity<>(loggedInUserDTO, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/loginFromMobile/{learnerUsername}")
    public ResponseEntity<LoggedInUserDTO> loginFromMobile(@PathVariable(value = "learnerUsername") String learnerUsername) throws Exception {
        try {
            Learner learner = learnerService.findLearnerByUsername(learnerUsername);
            LoggedInUserDTO loggedInUserDTO = new LoggedInUserDTO();
            loggedInUserDTO.setUsername(learnerUsername);
            return new ResponseEntity<>(loggedInUserDTO, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
