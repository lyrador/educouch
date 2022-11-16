package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.dto.ReelDTO;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.model.Reel;
import com.educouch.educouchsystem.service.ReelService;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reel")

public class ReelController {

    @Autowired
    ReelService reelService;

    @PostMapping("/createReel")
    public ResponseEntity<Reel> createQuiz(@RequestBody ReelDTO reelDTO) {
        try {
            Reel reel = reelService.createReel(reelDTO);
            reel.getCourseTag().setClassRuns(new ArrayList<>());
            reel.getCourseTag().setAssessments(new ArrayList<>());
            reel.getCourseTag().setInteractiveBooks(new ArrayList<>());
            reel.getCourseTag().setAssessments(new ArrayList<>());
            reel.getReelCreator().setClassRuns(new ArrayList<>());
            return new ResponseEntity<>(reel, HttpStatus.OK);
        } catch (CourseNotFoundException | InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllReelsByInstructorId/{instructorId}")
    public ResponseEntity<List<Reel>> getAllReelsByInstructorId(@PathVariable(value = "instructorId") Long instructorId) {
        try {
            List<Reel> reels = reelService.getAllReelsByInstructorId(instructorId);
            return new ResponseEntity<>(reels, HttpStatus.OK);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
