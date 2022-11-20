package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.QuestionDTO;
import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.QuestionBank;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.QuestionBankService;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionBankNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/questionBank")
@CrossOrigin
public class QuestionBankController {

    @Autowired
    QuizController quizController;

    @Autowired
    QuestionBankService questionBankService;

    @PostMapping("/createQuestionBank/{courseId}/{questionBankName}")
    public ResponseEntity<QuestionBank> createQuestionBank(@PathVariable(value="courseId") Long courseId, @PathVariable(value="questionBankName") String questionBankName) {
        try {
            QuestionBank questionBank = questionBankService.createQuestionBank(questionBankName, courseId);
            return new ResponseEntity<QuestionBank>(questionBank, HttpStatus.OK);
        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addQuestionToQuestionBank/{questionBankId}")
    public ResponseEntity<QuestionBank> addQuestionToQuestionBank(@PathVariable(value="questionBankId") Long questionBankId,
                                                              @RequestBody QuestionDTO questionDTO) {
        try {
            Question questionToDuplicate = quizController.convertQuestionDTOToQuestion(questionDTO);
            QuestionBank updatedQuestionBank = questionBankService.addQuestionToQuestionBank(questionToDuplicate, questionBankId);
            return new ResponseEntity<QuestionBank>(updatedQuestionBank, HttpStatus.OK);
        } catch (QuestionBankNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/removeQuestionFromQuestionBank/{questionBankId}/{questionToRemoveId}")
    public ResponseEntity<QuestionBank> removeQuestionFromQuestionBank(@PathVariable(value="questionBankId") Long questionBankId,
                                                                       @PathVariable(value="questionToRemoveId") Long questionToRemoveId) {
            try {
               QuestionBank updatedQuestionBank = questionBankService.removeQuestionFromQuestionBank(questionBankId,questionToRemoveId);
                return new ResponseEntity<QuestionBank>(updatedQuestionBank, HttpStatus.OK);
            } catch (QuestionBankNotFoundException | QuestionNotFoundException exception) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @PostMapping("/deleteQuestionBank/{questionBankId}")
    public ResponseEntity<Long> deleteQuestionBank(@PathVariable(value="questionBankId") Long questionBankId) {
        try {
            Long deletedQuestionBankId = questionBankService.deleteQuestionBank(questionBankId);
            return new ResponseEntity<Long>(deletedQuestionBankId, HttpStatus.OK);
        } catch (QuestionBankNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

}
