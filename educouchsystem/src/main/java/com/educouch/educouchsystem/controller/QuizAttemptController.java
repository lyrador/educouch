package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.*;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.QuizAttemptService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;
import com.educouch.educouchsystem.util.exception.QuizAttemptNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/quizAttempt")
@CrossOrigin
public class QuizAttemptController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizAttemptService quizAttemptService;

    @Autowired
    private QuizController quizController;

    @Autowired
    private LearnerService learnerService;

    @PostMapping("/createQuizAttempt/{assessmentId}/{learnerId}")
    public ResponseEntity<QuizAttemptDTO> createQuizAttempt(@PathVariable(value = "assessmentId") Long assessmentId,
                                                            @PathVariable(value = "learnerId") Long learnerId) {
        try {
            //getQuiz, run through all questions and create quizAttempt with empty questionAttempts
            Quiz quiz = quizService.retrieveQuizById(assessmentId);
            Learner learner = learnerService.getLearnerById(learnerId);
            QuizAttempt quizAttempt = initializeQuizAttempt(quiz);
            //save quizAttempt
            quizAttemptService.saveQuizAttempt(quiz, quizAttempt, learner);
            QuizAttemptDTO quizAttemptDTO = convertQuizAttemptToQuizAttemptDTO(quizAttempt);
            return new ResponseEntity<QuizAttemptDTO>(quizAttemptDTO, HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllQuizAttemptsByLearnerId/{learnerId}")
    public ResponseEntity<List<QuizAttemptDTO>> getAllQuizAttemptsByLearnerId(@PathVariable(value = "learnerId") Long learnerId) {
        try {
            List<QuizAttempt> quizAttempts = quizAttemptService.getQuizAttemptsByLearnerId(learnerId);
            List<QuizAttemptDTO> quizAttemptDTOS = convertQuizAttemptsToQuizAttemptDTOs(quizAttempts);
            return new ResponseEntity<>(quizAttemptDTOS, HttpStatus.OK);
        }  catch (NoQuizAttemptsFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/getParticularQuizAttemptsByLearnerId/{assessmentId}/{learnerId}")
    public ResponseEntity<List<QuizAttemptDTO>> getParticularQuizAttemptsByLearnerId(@PathVariable(value = "assessmentId") Long assessmentId,
                                                                                     @PathVariable(value = "learnerId") Long learnerId) {
        try {
            List<QuizAttempt> quizAttempts = quizAttemptService.getParticularQuizAttemptsByLearnerId(learnerId, assessmentId);
            List<QuizAttemptDTO> quizAttemptDTOS = convertQuizAttemptsToQuizAttemptDTOs(quizAttempts);
            return new ResponseEntity<>(quizAttemptDTOS, HttpStatus.OK);
        } catch (NoQuizAttemptsFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getMostRecentQuizAttemptByLearnerId/{assessmentId}/{learnerId}")
    public ResponseEntity<QuizAttemptDTO> getMostRecentQuizAttemptByLearnerId(@PathVariable(value = "assessmentId") Long assessmentId,
                                                                              @PathVariable(value = "learnerId") Long learnerId) {

        try {
            QuizAttempt mostRecentQuizAttempt = quizAttemptService.getMostRecentQuizAttemptByLearnerId(learnerId, assessmentId);
            QuizAttemptDTO quizAttemptDTO = convertQuizAttemptToQuizAttemptDTO(mostRecentQuizAttempt);
            return new ResponseEntity<>(quizAttemptDTO, HttpStatus.OK);
        } catch (NoQuizAttemptsFoundException exception) {
            return new ResponseEntity<>(new QuizAttemptDTO(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateQuizAttemptById/{quizId}")
    public ResponseEntity<QuizAttemptDTO> updateQuizAttemptById(@RequestBody QuizAttemptDTO updatedQuizAttemptDTO,
                                                                @PathVariable(value="quizId") Long quizAttemptId) {
        //try find existing quizAttempt
        try {
            QuizAttempt quizAttempt = quizAttemptService.getQuizAttemptById(quizAttemptId);
            Date updatedAttemptTime = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
            updatedQuizAttemptDTO.setLastAttemptTime(formatter.format(updatedAttemptTime));
            updatedQuizAttemptDTO.setAssessmentAttemptStatusEnum("INCOMPLETE");
            QuizAttempt updatedQuizAttempt = quizAttemptService.updateQuizAttempt(convertQuizAttemptDTOToQuizAttempt(updatedQuizAttemptDTO));
            //update quizAttempt
            return new ResponseEntity<>(updatedQuizAttemptDTO, HttpStatus.OK);
        }
        catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.valueOf("Date Parsing failed"));
        } catch (QuizAttemptNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/submitQuizAttempt/{quizId}")
    public ResponseEntity<QuizAttemptDTO> submitQuizAttempt(@RequestBody QuizAttemptDTO updatedQuizAttemptDTO,
                                                            @PathVariable(value="quizId") Long quizAttemptId) {

        //try find existing quizAttempt
        try {
            QuizAttempt quizAttempt = quizAttemptService.getQuizAttemptById(quizAttemptId);
            //update quizAttempt
            Date updatedAttemptTime = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
            updatedQuizAttemptDTO.setLastAttemptTime(formatter.format(updatedAttemptTime));
            updatedQuizAttemptDTO.setAssessmentAttemptStatusEnum("SUBMITTED");
            QuizAttempt updatedQuizAttempt = quizAttemptService.updateQuizAttempt(convertQuizAttemptDTOToQuizAttempt(updatedQuizAttemptDTO));
            //update state of quizAttempt to be "submitted"
            updatedQuizAttempt = quizAttemptService.submitQuizAttempt(updatedQuizAttempt);
            return new ResponseEntity<>(updatedQuizAttemptDTO, HttpStatus.OK);
        }
        catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.valueOf("Date Parsing failed"));
        } catch (QuizAttemptNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getNumberQuizAttemptsByLearnerId/{assessmentId}/{learnerId}")
    public ResponseEntity<Integer> getNumberQuizAttemptsByLearnerId(@PathVariable(value = "assessmentId") Long assessmentId,
                                                                    @PathVariable(value = "learnerId") Long learnerId) {
        try {
            List<QuizAttempt> quizAttempts = quizAttemptService.getParticularQuizAttemptsByLearnerId(learnerId, assessmentId);
            return new ResponseEntity<>(quizAttempts.size(), HttpStatus.OK);
        } catch (NoQuizAttemptsFoundException ex) {
            return new ResponseEntity<>(1,HttpStatus.OK);
        }
    }

    @GetMapping("/createQuizPreview/{assessmentId}")
    public ResponseEntity<QuizAttemptDTO> createQuizPreview(@PathVariable(value = "assessmentId") Long assessmentId) {
        try {
            //getQuiz, run through all questions and create quizAttempt with empty questionAttempts
            Quiz quiz = quizService.retrieveQuizById(assessmentId);
            QuizAttempt quizAttempt = initializeQuizAttempt(quiz);
            quizAttempt.setAttemptedQuiz(quiz);
            QuizAttemptDTO quizAttemptDTO = convertQuizAttemptToQuizAttemptDTO(quizAttempt);
            return new ResponseEntity<QuizAttemptDTO>(quizAttemptDTO, HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public QuizAttempt initializeQuizAttempt(Quiz quiz) {

        List<Question> questions = quiz.getQuizQuestions();

        List<QuestionAttempt> questionAttempts = new ArrayList<>();
        QuizAttempt quizAttempt = new QuizAttempt();

        if (quiz.getHasTimeLimit()) {
            quizAttempt.setTimeLimitRemaining(quiz.getTimeLimit());
        } else {
            quizAttempt.setTimeLimitRemaining(0);
        }

        for (Question q : questions) {
            QuestionAttempt questionAttempt = new QuestionAttempt();
            questionAttempt.setQuestionAttempted(q);
            questionAttempt.setOptionSelected(new Option(""));
            questionAttempt.setShortAnswerResponse("");
            questionAttempts.add(questionAttempt);
        }
        quizAttempt.setQuestionAttempts(questionAttempts);
        return quizAttempt;
    }

    public List<QuizAttemptDTO> convertQuizAttemptsToQuizAttemptDTOs(List<QuizAttempt> quizAttempts) {

        List<QuizAttemptDTO> quizAttemptDTOS = new ArrayList<>();
        for (QuizAttempt q : quizAttempts) {

            //convert quizAttempt to quizAttemptDTO
            QuizAttemptDTO quizAttemptDTO = convertQuizAttemptToQuizAttemptDTO(q);
            //convert questionAttempt to questionAttemptDTO
            List<QuestionAttemptDTO> questionAttemptDTOS = convertQuestionAttemptsToQuestionAttemptDTOs(q.getQuestionAttempts());
            //set questionAttemptDTO to quizAttemptDTO
            quizAttemptDTO.setQuestionAttempts(questionAttemptDTOS);
            //add quizAttemptDTO to list
            quizAttemptDTOS.add(quizAttemptDTO);
        }
        return quizAttemptDTOS;
    }

    public QuizAttemptDTO convertQuizAttemptToQuizAttemptDTO(QuizAttempt q) {
        QuizAttemptDTO quizAttemptDTO = new QuizAttemptDTO();
        quizAttemptDTO.setQuizAttemptId(q.getQuizAttemptId());
        quizAttemptDTO.setAttemptCounter(q.getAttemptCounter());
        quizAttemptDTO.setObtainedScore(q.getObtainedScore());
        quizAttemptDTO.setLastAttemptTime((q.getLastAttemptTime().toString()));
        quizAttemptDTO.setTimeLimitRemaining(q.getTimeLimitRemaining());
        if (q.getAssessmentAttemptStatusEnum().equals(AssessmentAttemptStatusEnum.INCOMPLETE)) {
            quizAttemptDTO.setAssessmentAttemptStatusEnum("INCOMPLETE");
        } else if (q.getAssessmentAttemptStatusEnum().equals(AssessmentAttemptStatusEnum.SUBMITTED)) {
            quizAttemptDTO.setAssessmentAttemptStatusEnum("SUBMITTED");
        } else {
            quizAttemptDTO.setAssessmentAttemptStatusEnum("GRADED");
        }
        quizAttemptDTO.setAttemptedQuiz(quizController.convertQuizToDTO(q.getAttemptedQuiz()));
        List<QuestionAttemptDTO> questionAttemptDTOS = convertQuestionAttemptsToQuestionAttemptDTOs(q.getQuestionAttempts());
        quizAttemptDTO.setQuestionAttempts(questionAttemptDTOS);

        return quizAttemptDTO;
    }

    public QuizAttempt convertQuizAttemptDTOToQuizAttempt(QuizAttemptDTO q) throws ParseException{
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuizAttemptId(q.getQuizAttemptId());
        quizAttempt.setAttemptCounter(q.getAttemptCounter());
        quizAttempt.setObtainedScore(q.getObtainedScore());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        quizAttempt.setLastAttemptTime(formatter.parse(q.getLastAttemptTime()));
        quizAttempt.setTimeLimitRemaining(q.getTimeLimitRemaining());
        if (q.getAssessmentAttemptStatusEnum().equals("INCOMPLETE")) {
            quizAttempt.setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum.INCOMPLETE);
        } else if (q.getAssessmentAttemptStatusEnum().equals("SUBMITTED")) {
            quizAttempt.setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum.SUBMITTED);
        } else {
            quizAttempt.setAssessmentAttemptStatusEnum(AssessmentAttemptStatusEnum.GRADED);
        }
        //convert questionAttemptDTOToquestionAttempt
        quizAttempt.setQuestionAttempts(convertQuestionAttemptDTOsToQuestionAttempt(q.getQuestionAttempts()));
        return quizAttempt;
    }

    public List<QuestionAttemptDTO> convertQuestionAttemptsToQuestionAttemptDTOs(List<QuestionAttempt> questionAttempts) {

        List<QuestionAttemptDTO> questionAttemptDTOS = new ArrayList<>();
        for (QuestionAttempt q : questionAttempts) {
            questionAttemptDTOS.add(convertQuestionAttemptToQuestionAttemptDTO(q));
        }
        return questionAttemptDTOS;
    }

    public List<QuestionAttempt> convertQuestionAttemptDTOsToQuestionAttempt(List<QuestionAttemptDTO> questionAttemptDTOS) {
        List<QuestionAttempt> questionAttempts = new ArrayList<>();
        for (QuestionAttemptDTO q : questionAttemptDTOS) {
            questionAttempts.add(convertQuestionAttemptDTOToQuestionAttempt(q));
        }
        return questionAttempts;
    }

    public QuestionAttempt convertQuestionAttemptDTOToQuestionAttempt(QuestionAttemptDTO q) {

        QuestionAttempt questionAttempt = new QuestionAttempt();
        questionAttempt.setQuestionAttemptId(q.getQuestionAttemptId());
        questionAttempt.setQuestionAttemptScore(q.getQuestionAttemptScore());
        questionAttempt.setShortAnswerResponse(q.getShortAnswerResponse());
        questionAttempt.setQuestionAttempted(quizController.convertQuestionDTOToQuestion(q.getQuestionAttempted()));
        questionAttempt.setOptionSelected(new Option(q.getOptionSelected()));
        questionAttempt.setFeedback(q.getFeedback());
        return questionAttempt;
    }

    public QuestionAttemptDTO convertQuestionAttemptToQuestionAttemptDTO(QuestionAttempt q) {

        QuestionAttemptDTO questionAttemptDTO = new QuestionAttemptDTO();
        questionAttemptDTO.setQuestionAttemptId(q.getQuestionAttemptId());
        questionAttemptDTO.setQuestionAttemptScore(q.getQuestionAttemptScore());
        questionAttemptDTO.setShortAnswerResponse(q.getShortAnswerResponse());
        questionAttemptDTO.setQuestionAttempted(quizController.convertQuestionToQuestionDTO(q.getQuestionAttempted()));
        questionAttemptDTO.setQuestionAttemptedQuestionId(q.getQuestionAttempted().getQuestionId().toString());
        questionAttemptDTO.setOptionSelected(q.getOptionSelected().getOptionContent());
        questionAttemptDTO.setFeedback(q.getFeedback());
        return questionAttemptDTO;
    }
}
