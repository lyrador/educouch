package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.PollQuestion;
import com.educouch.educouchsystem.model.PollQuestionResponse;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.PollQuestionResponseService;
import com.educouch.educouchsystem.service.PollQuestionService;
import com.educouch.educouchsystem.util.exception.PollQuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pollQuestionResponse")
@CrossOrigin
public class PollQuestionResponseController {

    @Autowired
    private PollQuestionResponseService pollQuestionResponseService;

    @Autowired
    private PollQuestionService pollQuestionService;

    @Autowired
    private LearnerService learnerService;

    @PostMapping("/{pollQuestionId}/{learnerId}/pollQuestionResponses")
    public ResponseEntity<PollQuestionResponse> addPollQuestionResponse (@PathVariable(value="pollQuestionId") Long pollQuestionId, @PathVariable(value="learnerId") Long learnerId, @RequestBody PollQuestionResponse pollQuestionResponseRequest) {
        try {
            PollQuestion pollQuestion = pollQuestionService.getPollQuestionById(pollQuestionId);
            Learner learner = learnerService.getLearnerById(learnerId);
            pollQuestionResponseRequest.setPollQuestion(pollQuestion);
            pollQuestionResponseRequest.setLearner(learner);

            if (pollQuestion.getPollQuestionResponses() != null) {
                pollQuestion.getPollQuestionResponses().add(pollQuestionResponseRequest);
            } else {
                List<PollQuestionResponse> pollQuestionResponseList = new ArrayList<>();
                pollQuestionResponseList.add(pollQuestionResponseRequest);
                pollQuestion.setPollQuestionResponses(pollQuestionResponseList);
            }

            if (learner.getPollQuestionResponses() != null) {
                learner.getPollQuestionResponses().add(pollQuestionResponseRequest);
            } else {
                List<PollQuestionResponse> pollQuestionResponseList = new ArrayList<>();
                pollQuestionResponseList.add(pollQuestionResponseRequest);
                learner.setPollQuestionResponses(pollQuestionResponseList);
            }

            PollQuestionResponse pollQuestionResponse = pollQuestionResponseService.savePollQuestionResponse(pollQuestionResponseRequest);
            return new ResponseEntity<>(pollQuestionResponse, HttpStatus.OK);

        } catch (PollQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pollQuestionResponsesFromQuestion")
    public ResponseEntity<List<PollQuestionResponse>> getAllPollQuestionResponsesFromQuestion() {
        List<PollQuestionResponse> allPollQuestionResponses = new ArrayList<>();
        if (!pollQuestionService.getAllPollQuestions().isEmpty()) {
            allPollQuestionResponses = pollQuestionResponseService.getAllPollQuestionResponse();
            return new ResponseEntity<>(allPollQuestionResponses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
