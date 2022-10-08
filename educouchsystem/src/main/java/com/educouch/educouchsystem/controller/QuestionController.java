package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.OptionDTO;
import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.service.OptionService;
import com.educouch.educouchsystem.service.QuestionService;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.OptionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;

//    @PostMapping("/addNewOption/{questionId}")
//    public ResponseEntity<Option> addOptionToQuestion(@RequestBody OptionDTO optionDTO, @PathVariable(value="questionId") Long questionId) {
//        try {
//            Question question = questionService.retrieveQuestionById(questionId);
//            Option newOption = new Option();
//
//            newOption.setOptionContent(optionDTO.getOptionContent());
//
//            if (optionDTO.getOptionIsCorrect().equals("true")) {
//                newOption.setCorrect(Boolean.TRUE);
//            } else if (optionDTO.getOptionIsCorrect().equals("false")) {
//                newOption.setCorrect(Boolean.FALSE);
//            }
//
//            questionService.addOptionToQuestion(questionId, newOption);
//            return new ResponseEntity<>(newOption, HttpStatus.OK);
//        } catch (QuestionNotFoundException | EntityInstanceExistsInCollectionException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @DeleteMapping("/deleteOptionByIdFromQuestionId/{optionId}/{questionId}")
//    public ResponseEntity<HttpStatus> deleteOptionByIdFromQuestionId(@PathVariable("questionId") Long optionId, @PathVariable("quizId") Long questionId) {
//        try {
//            questionService.deleteOptionFromQuestionId(optionId, questionId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (QuestionNotFoundException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/updateOption/{optionId}")
    public ResponseEntity<Option> updateOption(@RequestBody OptionDTO optionDTO, @PathVariable("optionId") Long optionId) {
        try {
            Option optionToUpdate = optionService.retrieveOptionById(optionId);

            optionToUpdate.setOptionContent(optionDTO.getOptionContent());

            if (optionDTO.getOptionIsCorrect().equals("true")) {
                optionToUpdate.setCorrect(Boolean.TRUE);
            } else if (optionDTO.getOptionIsCorrect().equals("false")) {
                optionToUpdate.setCorrect(Boolean.FALSE);
            }

            optionService.updateOption(optionToUpdate, optionToUpdate);
            return new ResponseEntity<Option>(optionService.saveOption(optionToUpdate), HttpStatus.OK);
        } catch (OptionNotFoundException ex) {
            return new ResponseEntity<Option>(HttpStatus.NOT_FOUND);
        }
    }
}
