package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Poll;
import com.educouch.educouchsystem.model.TriviaQuiz;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.PollService;
import com.educouch.educouchsystem.util.exception.PollNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/poll")
@CrossOrigin
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private ClassRunService classRunService;

    @PostMapping("/classRun/{classRunId}/poll")
    public ResponseEntity<Poll> addPoll(@PathVariable(value = "classRunId") Long classRunId, @RequestBody Poll pollRequest) {
        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        pollRequest.setClassRun(classRun);
        if (classRun.getPolls() != null) {
            classRun.getPolls().add(pollRequest);
        } else {
            List<Poll> classRunPolls = new ArrayList<>();
            classRunPolls.add(pollRequest);
            classRun.setPolls(classRunPolls);
        }

        pollRequest.setCreationDate(new Date());
        Poll poll = pollService.savePoll(pollRequest);
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    @GetMapping("/polls")
    public ResponseEntity<List<Poll>> getALlPolls() {
        List<Poll> allPolls = new ArrayList<>();
        if (!pollService.getAllPolls().isEmpty()) {
            allPolls = pollService.getAllPolls();

            return new ResponseEntity<>(allPolls, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{pollId}/polls")
    public ResponseEntity<Poll> getPollById(@PathVariable("pollId") Long pollId) {
        try {
            Poll existingPoll = pollService.getPollById(pollId);
            return new ResponseEntity<>(existingPoll, HttpStatus.OK);
        } catch (PollNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<Poll> deletePoll(@PathVariable("pollId") Long pollId) {
        try {
            Poll existingPoll = pollService.getPollById(pollId);
            ClassRun classRun = existingPoll.getClassRun();
            classRun.getPolls().remove(existingPoll);
            existingPoll.setClassRun(null);
            pollService.deletePoll(pollId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PollNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/polls/{pollId}")
    public ResponseEntity<Poll> updatePoll(@RequestBody Poll poll, @PathVariable(value = "pollId") Long pollId) {
        try {
            Poll existingPoll = pollService.getPollById(pollId);
            existingPoll.setPollTitle(poll.getPollTitle());
            existingPoll.setPollDescription(poll.getPollDescription());
            existingPoll.setNumOfQuestions(poll.getNumOfQuestions());
            pollService.savePoll(existingPoll);

            return new ResponseEntity<>(existingPoll, HttpStatus.OK);
        } catch (PollNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pollsFromClassRun/{classRunId}")
    public ResponseEntity<List<Poll>> getAllPollsByClassRunId (@PathVariable(value = "classRunId") Long classRunId) {
        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        List<Poll> polls = new ArrayList<>();
        polls.addAll(classRun.getPolls());

        return new ResponseEntity<>(polls, HttpStatus.OK);
    }





}
