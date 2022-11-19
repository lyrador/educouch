package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Poll;
import com.educouch.educouchsystem.model.PollQuestion;
import com.educouch.educouchsystem.service.PollQuestionService;
import com.educouch.educouchsystem.service.PollService;
import com.educouch.educouchsystem.util.exception.PollNotFoundException;
import com.educouch.educouchsystem.util.exception.PollQuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pollQuestion")
@CrossOrigin
public class PollQuestionController {

    @Autowired
    private PollQuestionService pollQuestionService;

    @Autowired
    private PollService pollService;

    @PostMapping("/{pollId}/pollQuestions")
    public ResponseEntity<PollQuestion> addPollQuestion(@PathVariable(value="pollId") Long pollId, @RequestBody PollQuestion pollQuestionRequest) {
        try {
            Poll poll = pollService.getPollById(pollId);
            pollQuestionRequest.setPoll(poll);
            if (poll.getPollQuestions() != null) {
                poll.getPollQuestions().add(pollQuestionRequest);
                pollQuestionRequest.setPollQuestionNumber(poll.getPollQuestions().size() + 1);
            } else {
                List<PollQuestion> pollQuestionList = new ArrayList<>();
                pollQuestionRequest.setPollQuestionNumber(1);
                pollQuestionList.add(pollQuestionRequest);
                poll.setPollQuestions(pollQuestionList);
            }

            if(pollQuestionRequest.getPoll() != null) {
                if (pollQuestionRequest.getPoll().getPollQuestions() != null) {
                    pollQuestionRequest.setPollQuestionNumber(pollQuestionRequest.getPoll().getPollQuestions().size());
                    poll.setNumOfQuestions(pollQuestionRequest.getPoll().getPollQuestions().size());
                }
            }

            PollQuestion pollQuestion = pollQuestionService.savePollQuestion(pollQuestionRequest);
            pollService.savePoll(poll);

            return new ResponseEntity<>(pollQuestion, HttpStatus.OK);
        } catch (PollNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pollQuestions")
    public ResponseEntity<List<PollQuestion>> getAllPollQuestions() {
        List<PollQuestion> allPollQuestions = new ArrayList<>();
        if (!pollQuestionService.getAllPollQuestions().isEmpty()){
            allPollQuestions = pollQuestionService.getAllPollQuestions();
            return new ResponseEntity<>(allPollQuestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{pollQuestionId}/pollQuestions")
    public ResponseEntity<PollQuestion> getPollQuestionById(@PathVariable("pollQuestionId") Long pollQuestionId) {
        try {
            PollQuestion pollQuestion = pollQuestionService.getPollQuestionById(pollQuestionId);
            return new ResponseEntity<>(pollQuestion, HttpStatus.OK);
        } catch (PollQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/pollQuestions/{pollQuestionId}")
    public ResponseEntity<PollQuestion> deletePollQuestion(@PathVariable("pollQuestionId") Long pollQuestionId) {
        try {
            PollQuestion existingPollQuestion = pollQuestionService.getPollQuestionById(pollQuestionId);
            Poll poll = existingPollQuestion.getPoll();

            List<PollQuestion> pollQuestionList = poll.getPollQuestions();
            if (pollQuestionList.size() != existingPollQuestion.getPollQuestionNumber()) {
                for(int i = existingPollQuestion.getPollQuestionNumber(); i < pollQuestionList.size(); i++) {
                    PollQuestion pollQuestionToUpdate = pollQuestionList.get(i);
                    Integer updatedQuestionNumber = pollQuestionToUpdate.getPollQuestionNumber() - 1;
                    pollQuestionToUpdate.setPollQuestionNumber(updatedQuestionNumber);
                    pollQuestionService.savePollQuestion(pollQuestionToUpdate);
                }
            }

            poll.getPollQuestions().remove(existingPollQuestion);
            existingPollQuestion.setPoll(null);

            pollQuestionService.deletePollQuestion(pollQuestionId);

            poll.setNumOfQuestions(poll.getPollQuestions().size());
            pollService.savePoll(poll);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PollQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/pollQuestions/{pollId}/deleteAll")
    public ResponseEntity<Poll> deleteAllPollQuestionsOfPoll (@PathVariable("pollId") Long pollId) {
        try {
            Poll existingPoll = pollService.getPollById(pollId);
            existingPoll.getPollQuestions().clear();
            pollService.savePoll(existingPoll);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PollNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pollQuestions/{pollQuestionId}")
    public ResponseEntity<PollQuestion> updatePollQuestion(@RequestBody PollQuestion pollQuestion, @PathVariable("pollQuestionId") Long pollQuestionId) {
        try {
            PollQuestion existingPollQuestion = pollQuestionService.getPollQuestionById(pollQuestionId);
            existingPollQuestion.setPollQuestionTitle(pollQuestion.getPollQuestionTitle());
            existingPollQuestion.setHasTimeLimit(pollQuestion.getHasTimeLimit());
            existingPollQuestion.setQuestionTimeLimit(pollQuestion.getQuestionTimeLimit());

            pollQuestionService.savePollQuestion(existingPollQuestion);
            return new ResponseEntity<>(existingPollQuestion,HttpStatus.OK);
        } catch (PollQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/poll/{pollId}/pollQuestions")
    public ResponseEntity<List<PollQuestion>> getAllPollQuestionsByPollId(@PathVariable("pollId") Long pollId) {
        try {
            Poll existingPoll = pollService.getPollById(pollId);
            List<PollQuestion> pollQuestionList = new ArrayList<>();
            pollQuestionList.addAll((existingPoll.getPollQuestions()));
            return new ResponseEntity<>(pollQuestionList, HttpStatus.OK);
        } catch (PollNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




}
