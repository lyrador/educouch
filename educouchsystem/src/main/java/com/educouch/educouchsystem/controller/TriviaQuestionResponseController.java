package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.TriviaOption;
import com.educouch.educouchsystem.model.TriviaQuestion;
import com.educouch.educouchsystem.model.TriviaQuestionResponse;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.TriviaOptionService;
import com.educouch.educouchsystem.service.TriviaQuestionResponseService;
import com.educouch.educouchsystem.service.TriviaQuestionService;
import com.educouch.educouchsystem.util.exception.TriviaOptionNotFoundException;
import com.educouch.educouchsystem.util.exception.TriviaQuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/triviaQuestionResponse")
@CrossOrigin
public class TriviaQuestionResponseController {

    @Autowired
    private TriviaQuestionResponseService triviaQuestionResponseService;

    @Autowired
    private TriviaQuestionService triviaQuestionService;

    @Autowired
    private TriviaOptionService triviaOptionService;

    @Autowired
    private LearnerService learnerService;

    @PostMapping("/{triviaQuestionId}/{learnerId}/{triviaOptionId}/triviaQuestionResponses")
    public ResponseEntity<TriviaQuestionResponse> addTriviaQuestionResponse(@PathVariable(value="triviaQuestionId") Long triviaQuestionId, @PathVariable(value="learnerId") Long learnerId,  @PathVariable(value="triviaOptionId") Long triviaOptionId, @RequestBody TriviaQuestionResponse triviaQuestionResponseRequest) {
        try {

            // for this question response, there are both learner and trivia questions as path variables
            // because when a question response is generated, it has to be set to both the question and the learner as well
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            Learner learner = learnerService.getLearnerById(learnerId);
            TriviaOption optionChosen = triviaOptionService.getTriviaOptionById(triviaOptionId);
            triviaQuestionResponseRequest.setTriviaQuestion(triviaQuestion);
            triviaQuestionResponseRequest.setLearner(learner);
            triviaQuestionResponseRequest.setOptionChosen(optionChosen);

            if (triviaQuestion.getTriviaQuestionResponses() != null) {
                triviaQuestion.getTriviaQuestionResponses().add(triviaQuestionResponseRequest);
            } else {
                List<TriviaQuestionResponse> triviaQuestionResponseList = new ArrayList<>();
                triviaQuestionResponseList.add(triviaQuestionResponseRequest);
                triviaQuestion.setTriviaQuestionResponses(triviaQuestionResponseList);
            }

            if (learner.getTriviaQuestionResponses() != null) {
                learner.getTriviaQuestionResponses().add(triviaQuestionResponseRequest);
            } else {
                List<TriviaQuestionResponse> triviaQuestionResponseList = new ArrayList<>();
                triviaQuestionResponseList.add(triviaQuestionResponseRequest);
                learner.setTriviaQuestionResponses(triviaQuestionResponseList);
            }

            TriviaQuestionResponse triviaQuestionResponse = triviaQuestionResponseService.saveTriviaQuestionResponse(triviaQuestionResponseRequest);
            return new ResponseEntity<>(triviaQuestionResponse, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TriviaOptionNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuestionResponsesFromQuestion")
    public ResponseEntity<List<TriviaQuestionResponse>> getAllTriviaResponsesFromQuestion() {
        List<TriviaQuestionResponse> allTriviaQuestionResponses = new ArrayList<>();
        if (!triviaQuestionService.getAllTriviaQuestions().isEmpty()) {
            allTriviaQuestionResponses = triviaQuestionResponseService.getAllTriviaQuestionResponses();
            return new ResponseEntity<>(allTriviaQuestionResponses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
