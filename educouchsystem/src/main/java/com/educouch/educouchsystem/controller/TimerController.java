package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ForumDTO;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/timer")
@CrossOrigin
public class TimerController {

    @Autowired
    TimerService timerService;

    @GetMapping("/testDailyCheckCourseEnrolmentStatus")
    public void getAllForumsByCourseId () {
        timerService.convertEnrolmentStatus();
    }



}
