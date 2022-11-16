package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.TriviaQuestion;
import com.educouch.educouchsystem.model.TriviaQuiz;
import com.educouch.educouchsystem.service.TriviaQuestionService;
import com.educouch.educouchsystem.service.TriviaQuizService;
import com.educouch.educouchsystem.util.exception.TriviaQuestionNotFoundException;
import com.educouch.educouchsystem.util.exception.TriviaQuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/triviaQuestion")
@CrossOrigin
public class TriviaQuestionController {

    @Autowired
    private TriviaQuestionService triviaQuestionService;

    @Autowired
    private TriviaQuizService triviaQuizService;

    @PostMapping("/{triviaQuizId}/triviaQuestions")
    public ResponseEntity<TriviaQuestion> addTriviaQuestion(@PathVariable(value="triviaQuizId") Long triviaQuizId, @RequestBody TriviaQuestion triviaQuestionRequest) {
        try {
            TriviaQuiz triviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            triviaQuestionRequest.setTriviaQuiz(triviaQuiz);
            if (triviaQuiz.getTriviaQuestions() != null) {
                triviaQuiz.getTriviaQuestions().add(triviaQuestionRequest);
                triviaQuestionRequest.setQuestionNumber(triviaQuiz.getTriviaQuestions().size() + 1);
            } else {
                List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
                triviaQuestionRequest.setQuestionNumber(1);
                triviaQuestionList.add(triviaQuestionRequest);
                triviaQuiz.setTriviaQuestions(triviaQuestionList);
            }

            if(triviaQuestionRequest.getTriviaQuiz() != null) {
                if (triviaQuestionRequest.getTriviaQuiz().getTriviaQuestions() != null) {
                    triviaQuestionRequest.setQuestionNumber(triviaQuestionRequest.getTriviaQuiz().getTriviaQuestions().size());
                    triviaQuiz.setNumOfQuestions(triviaQuestionRequest.getTriviaQuiz().getTriviaQuestions().size());

                }
            }
            TriviaQuestion triviaQuestion = triviaQuestionService.saveTriviaQuestion(triviaQuestionRequest);
            triviaQuizService.saveTriviaQuiz(triviaQuiz);

            return new ResponseEntity<>(triviaQuestion, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuestions")
    public ResponseEntity<List<TriviaQuestion>> getAllTriviaQuestions() {
        List<TriviaQuestion> allTriviaQuestions = new ArrayList<>();
        if (!triviaQuizService.getAllTriviaQuizzes().isEmpty()) {
            allTriviaQuestions = triviaQuestionService.getAllTriviaQuestions();
            return new ResponseEntity<>(allTriviaQuestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{triviaQuestionId}/triviaQuestions")
    public ResponseEntity<TriviaQuestion> getTriviaQuestionById(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            return new ResponseEntity<>(triviaQuestion, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/triviaQuestions/{triviaQuestionId}")
    public ResponseEntity<TriviaQuestion> deleteTriviaQuestion(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion existingTriviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            TriviaQuiz triviaQuiz = existingTriviaQuestion.getTriviaQuiz();

            List<TriviaQuestion> triviaQuestionList = triviaQuiz.getTriviaQuestions();
            if (triviaQuestionList.size() != existingTriviaQuestion.getQuestionNumber()) {
                for (int i = existingTriviaQuestion.getQuestionNumber(); i < triviaQuestionList.size(); i++) {
                    TriviaQuestion triviaQuestionToUpdate = triviaQuestionList.get(i);
                    Integer updatedQuestionNumber = triviaQuestionToUpdate.getQuestionNumber() - 1;
                    triviaQuestionToUpdate.setQuestionNumber(updatedQuestionNumber);
                    triviaQuestionService.saveTriviaQuestion(triviaQuestionToUpdate);
                }
            }

            triviaQuiz.getTriviaQuestions().remove(existingTriviaQuestion);
            existingTriviaQuestion.setTriviaQuiz(null);

            triviaQuestionService.deleteTriviaQuestion(triviaQuestionId);

            triviaQuiz.setNumOfQuestions(triviaQuiz.getTriviaQuestions().size());
            triviaQuizService.saveTriviaQuiz(triviaQuiz);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/triviaQuestions/{triviaQuizId}/deleteAll")
    public ResponseEntity<TriviaQuestion> deleteAllTriviaQuestionsOfQuiz (@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            existingTriviaQuiz.getTriviaQuestions().clear();
            triviaQuizService.saveTriviaQuiz(existingTriviaQuiz);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/triviaQuestions/{triviaQuestionId}")
    public ResponseEntity<TriviaQuestion> updateTriviaQuestion (@RequestBody TriviaQuestion triviaQuestion, @PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion existingTriviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            existingTriviaQuestion.setQuestionTitle(triviaQuestion.getQuestionTitle());
            existingTriviaQuestion.setQuestionTimeLimit(triviaQuestion.getQuestionTimeLimit());
            existingTriviaQuestion.setHasTimeLimit(triviaQuestion.getHasTimeLimit());

            triviaQuestionService.saveTriviaQuestion(existingTriviaQuestion);
            return new ResponseEntity<>(existingTriviaQuestion, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuiz/{triviaQuizId}/triviaQuestions")
    public ResponseEntity<List<TriviaQuestion>> getAllTriviaQuestionsByTriviaQuizId(@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
            triviaQuestionList.addAll(existingTriviaQuiz.getTriviaQuestions());
            return new ResponseEntity<>(triviaQuestionList, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
