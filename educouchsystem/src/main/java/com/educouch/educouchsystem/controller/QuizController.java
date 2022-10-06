package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.QuestionDTO;
import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.QuestionService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.enumeration.AssessmentStatusEnum;
import com.educouch.educouchsystem.util.enumeration.QuestionTypeEnum;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/createQuiz/{courseId}")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDTO quizDTO, @PathVariable(value="courseId") Long courseId) {

        //Instantiate quiz
        try {
            Quiz instantiatedQuiz = instantiateQuiz(quizDTO, courseId);
            //Add questions
            return new ResponseEntity<>(new Quiz(), HttpStatus.OK);
        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException ex) {
            return new ResponseEntity<>(new Quiz(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addNewQuestion/{quizId}")
    public ResponseEntity<Question> addQuestionToQuiz(@RequestBody QuestionDTO questionDTO, @PathVariable(value="quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            Question newQuestion = new Question();

            newQuestion.setQuestionContent(questionDTO.getQuestionContent());
            newQuestion.setQuestionHint(questionDTO.getQuestionHint());
            newQuestion.setQuestionMaxScore(questionDTO.getQuestionMaxScore());

            if (questionDTO.getQuestionType().equals("TRUE FALSE")) {
                newQuestion.setQuestionType(QuestionTypeEnum.TRUE_FALSE);
            } else if (questionDTO.getQuestionType().equals("OPEN ENDED")) {
                newQuestion.setQuestionType(QuestionTypeEnum.OPEN_ENDED);
            } else if (questionDTO.getQuestionType().equals("MCQ")) {
                newQuestion.setQuestionType(QuestionTypeEnum.MCQ);
            } else if (questionDTO.getQuestionType().equals("MRQ")) {
                newQuestion.setQuestionType(QuestionTypeEnum.MRQ);
            }

            quizService.addQuestionToQuiz(quizId, newQuestion);
            return new ResponseEntity<>(newQuestion, HttpStatus.OK);
        } catch (QuizNotFoundException | EntityInstanceExistsInCollectionException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteQuestionByIdFromQuizId/{questionId}/{quizId}")
    public ResponseEntity<HttpStatus> deleteQuestionByIdFromQuizId(@PathVariable("questionId") Long questionId, @PathVariable("quizId") Long quizId) {
        try {
            quizService.deleteQuestionFromQuizId(questionId, quizId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (QuizNotFoundException | QuestionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllQuestionsByQuizId/{quizId}")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsByQuizId(@PathVariable(value="quizId") Long quizId) {
        try {
            List<Question> questions = quizService.getAllQuestionsInQuiz(quizId);
            List<QuestionDTO> questionDTOs = new ArrayList<>();
            for (Question question : questions) {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(question.getQuestionId());
                questionDTO.setQuestionContent(question.getQuestionContent());
                questionDTO.setQuestionHint(question.getQuestionHint());
                questionDTO.setQuestionMaxScore(question.getQuestionMaxScore());

                if (question.getQuestionType() == QuestionTypeEnum.TRUE_FALSE) {
                    questionDTO.setQuestionType("TRUE FALSE");
                } else if (question.getQuestionType() == QuestionTypeEnum.OPEN_ENDED) {
                    questionDTO.setQuestionType("OPEN ENDED");
                } else if (question.getQuestionType() == QuestionTypeEnum.MCQ) {
                    questionDTO.setQuestionType("MCQ");
                } else if (question.getQuestionType() == QuestionTypeEnum.MRQ) {
                    questionDTO.setQuestionType("MRQ");
                }

                questionDTOs.add(questionDTO);
            }
            return new ResponseEntity<>(questionDTOs, HttpStatus.OK);

        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateQuestion/{questionId}")
    public ResponseEntity<Question> updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable("questionId") Long questionId) {
        try {
            Question questionToUpdate = questionService.retrieveQuestionById(questionId);

            questionToUpdate.setQuestionContent(questionDTO.getQuestionContent());
            questionToUpdate.setQuestionHint(questionDTO.getQuestionHint());
            questionToUpdate.setQuestionMaxScore(questionDTO.getQuestionMaxScore());

            if (questionDTO.getQuestionType().equals("TRUE FALSE")) {
                questionToUpdate.setQuestionType(QuestionTypeEnum.TRUE_FALSE);
            } else if (questionDTO.getQuestionType().equals("OPEN ENDED")) {
                questionToUpdate.setQuestionType(QuestionTypeEnum.OPEN_ENDED);
            } else if (questionDTO.getQuestionType().equals("MCQ")) {
                questionToUpdate.setQuestionType(QuestionTypeEnum.MCQ);
            } else if (questionDTO.getQuestionType().equals("MRQ")) {
                questionToUpdate.setQuestionType(QuestionTypeEnum.MRQ);
            }

            questionService.updateQuestion(questionToUpdate, questionToUpdate);
            return new ResponseEntity<Question>(questionService.saveQuestion(questionToUpdate), HttpStatus.OK);
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    public Quiz instantiateQuiz(QuizDTO quizDTO, Long courseId) throws CourseNotFoundException, ParseException{

            Quiz newQuiz = new Quiz();
            newQuiz.setTitle(quizDTO.getAssessmentTitle());
            newQuiz.setDescription(quizDTO.getAssessmentDescription());
            newQuiz.setMaxScore(quizDTO.getAssessmentMaxScore());

            newQuiz.setOpen(Boolean.FALSE);

            if (quizDTO.getAssessmentHasTimeLimit().equals("true")) {
                newQuiz.setHasTimeLimit(Boolean.TRUE);
            } else if (quizDTO.getAssessmentHasTimeLimit().equals("false")) {
                newQuiz.setHasTimeLimit(Boolean.FALSE);
            }

            if (quizDTO.getAssessmentIsAutoRelease().equals("true")) {
                newQuiz.setAutoRelease(Boolean.TRUE);
            } else if (quizDTO.getAssessmentIsAutoRelease().equals("false")) {
                newQuiz.setAutoRelease(Boolean.FALSE);
            }

            newQuiz.setAssessmentStatus(AssessmentStatusEnum.PENDING);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date) formatter.parse(quizDTO.getAssessmentStartDate());
            Date endDate = (Date) formatter.parse(quizDTO.getAssessmentEndDate());
            newQuiz.setStartDate(startDate);
            newQuiz.setEndDate(endDate);

            quizService.saveQuiz(courseId, newQuiz);
            return newQuiz;
        }
    }
