package com.educouch.educouchsystem.controller;


import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.QuestionService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.EntityInstanceExistsInCollectionException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/addQuestion/{quizId}")
    public ResponseEntity<Question> addQuestionToQuiz (@RequestBody Question question, @PathVariable(value="quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            quizService.addQuestionToQuiz(quizId, question);
            return new ResponseEntity<Question>(HttpStatus.OK);
        } catch (QuizNotFoundException | EntityInstanceExistsInCollectionException ex) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/removeQuestion/{quizId}")
    public ResponseEntity<Question> removeQuestionFromQuiz (@RequestBody Question question, @PathVariable(value="quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            quizService.removeQuestionFromQuiz(quizId, question);
            return new ResponseEntity<Question>(HttpStatus.OK);
        } catch (QuestionNotFoundException | QuizNotFoundException ex) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllQuestionsByQuizId/{quizId}")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizId (@PathVariable(value="quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            List<Question> questions = new ArrayList<Question>();
            questions.addAll(quiz.getQuizQuestions());
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getQuestionById/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("questionId") Long questionId) {
        try {
            Question question = questionService.retrieveQuestionById(questionId);
            return new ResponseEntity<Question>(question, HttpStatus.OK);
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteQuestion/{questionId}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable("questionId") Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        try {
            Question toUpdateQuestion = questionService.updateQuestion(question);
            return new ResponseEntity<Question>(toUpdateQuestion, HttpStatus.OK);
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }
}
