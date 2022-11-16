package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.TriviaOption;
import com.educouch.educouchsystem.model.TriviaQuestion;
import com.educouch.educouchsystem.service.TriviaOptionService;
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
@RequestMapping("/triviaOption")
@CrossOrigin
public class TriviaOptionController {

    @Autowired
    private TriviaQuestionService triviaQuestionService;

    @Autowired
    private TriviaOptionService triviaOptionService;

    @PostMapping("/{triviaQuestionId}/triviaOptions")
    public ResponseEntity<TriviaOption> addTriviaOption(@PathVariable(value = "triviaQuestionId") Long triviaQuestionId, @RequestBody TriviaOption triviaOptionRequest) {
        try {
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            triviaOptionRequest.setTriviaQuestion(triviaQuestion);
            triviaOptionRequest.setOptionNumber(1);
            System.out.println(triviaOptionRequest.getOptionContent());

            if(triviaQuestion.getTriviaOptions() != null) {
                triviaQuestion.getTriviaOptions().add(triviaOptionRequest);
            } else {
                List<TriviaOption> triviaOptionList = new ArrayList<>();
                triviaOptionList.add(triviaOptionRequest);
                triviaQuestion.setTriviaOptions(triviaOptionList);
            }

            if (triviaOptionRequest.getTriviaQuestion() != null) {
                if (triviaOptionRequest.getTriviaQuestion().getTriviaOptions() != null) {
                    triviaOptionRequest.setOptionNumber(triviaOptionRequest.getTriviaQuestion().getTriviaOptions().size());
                }
            }

            TriviaOption triviaOption = triviaOptionService.saveTriviaOption(triviaOptionRequest);
            return new ResponseEntity<>(triviaOption, HttpStatus.OK);

        } catch (TriviaQuestionNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaOptions")
    public ResponseEntity<List<TriviaOption>> getAllTriviaOptions() {
        List<TriviaOption> allTriviaOptions = new ArrayList<>();
        if (!triviaQuestionService.getAllTriviaQuestions().isEmpty()) {
            allTriviaOptions = triviaOptionService.getAllTriviaOptions();
            return new ResponseEntity<>(allTriviaOptions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{triviaOptionId}/triviaOptions")
    public ResponseEntity<TriviaOption> getTriviaOptionById(@PathVariable("triviaOptionId") Long triviaOptionId) {
        try {
            TriviaOption triviaOption = triviaOptionService.getTriviaOptionById(triviaOptionId);
            return new ResponseEntity<>(triviaOption, HttpStatus.OK);
        } catch (TriviaOptionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/triviaOptions/{triviaOptionId}")
    public ResponseEntity<TriviaOption> deleteTriviaOption(@PathVariable("triviaOptionId") Long triviaOptionId) {
        try {
            TriviaOption existingTriviaOption = triviaOptionService.getTriviaOptionById(triviaOptionId);
            TriviaQuestion triviaQuestion = existingTriviaOption.getTriviaQuestion();

            List<TriviaOption> triviaOptionList = triviaQuestion.getTriviaOptions();
            if (triviaOptionList.size() != existingTriviaOption.getOptionNumber()) {
                for (int i = existingTriviaOption.getOptionNumber(); i < triviaOptionList.size(); i++) {
                    TriviaOption triviaOptionToUpdate = triviaOptionList.get(i);
                    Integer updatedOptionNumber = triviaOptionToUpdate.getOptionNumber() - 1;
                    triviaOptionToUpdate.setOptionNumber(updatedOptionNumber);
                    triviaOptionService.saveTriviaOption(triviaOptionToUpdate);
                }
            }

            triviaQuestion.getTriviaOptions().remove(existingTriviaOption);
            existingTriviaOption.setTriviaQuestion(null);
            triviaOptionService.deleteTriviaOption(triviaOptionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TriviaOptionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/triviaOptions/{triviaQuestionId}/deleteAll")
    public ResponseEntity<TriviaOption> deleteAllTriviaOptionsOfQuestion(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            List<TriviaOption> triviaOptionList = triviaQuestion.getTriviaOptions();
            for (TriviaOption triviaOption : triviaOptionList) {
                triviaOptionService.deleteTriviaOption(triviaOption.getTriviaOptionId());
            }
            triviaQuestion.getTriviaOptions().clear();
            triviaQuestionService.saveTriviaQuestion(triviaQuestion);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TriviaQuestionNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/triviaOptions/{triviaOptionId}")
    public ResponseEntity<TriviaOption> updateTriviaOption(@RequestBody TriviaOption triviaOption, @PathVariable("triviaOptionId") Long triviaOptionId) {
        try {
            TriviaOption existingTriviaOption = triviaOptionService.getTriviaOptionById(triviaOptionId);
            existingTriviaOption.setOptionContent(triviaOption.getOptionContent());
            existingTriviaOption.setCorrectAnswer(triviaOption.getCorrectAnswer());

            triviaOptionService.saveTriviaOption(existingTriviaOption);
            return new ResponseEntity<>(existingTriviaOption, HttpStatus.OK);
        } catch (TriviaOptionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/triviaQuestion/{triviaQuestionId}/triviaOptions")
    public ResponseEntity<List<TriviaOption>> getAllTriviaOptionsByQuestionId(@PathVariable("triviaQuestionId") Long triviaQuestionId) {
        try {
            TriviaQuestion triviaQuestion = triviaQuestionService.getTriviaQuestionById(triviaQuestionId);
            List<TriviaOption> triviaOptionList = new ArrayList<>();
            triviaOptionList.addAll(triviaQuestion.getTriviaOptions());
            return new ResponseEntity<>(triviaOptionList, HttpStatus.OK);
        } catch (TriviaQuestionNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
