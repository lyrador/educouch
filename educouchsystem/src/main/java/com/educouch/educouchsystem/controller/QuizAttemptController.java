package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.*;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.QuizAttemptService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;
import com.educouch.educouchsystem.util.exception.NoQuizAttemptsFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<QuizAttempt> initialiseQuizAttempt(@PathVariable(value = "assessmentId") Long assessmentId,
                                                             @PathVariable(value = "learnerId") Long learnerId) {
        try {
            //getQuiz, run through all questions and create quizAttempt with empty questionAttempts
            Quiz quiz = quizService.retrieveQuizById(assessmentId);
            Learner learner = learnerService.getLearnerById(learnerId);
            QuizAttempt quizAttempt = initializeQuizAttempt(quiz);
            //save quizAttempt
            quizAttemptService.saveQuizAttempt(quiz, quizAttempt, learner);
            return new ResponseEntity<>(quizAttempt, HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    
    @PutMapping("/updateQuizAttempt")
    public ResponseEntity<QuizAttemptDTO> updateQuizAttempt(@RequestBody QuizAttemptDTO updatedQuizAttemptDTO) {
        //try find existing quizAttempt

        //update quizAttempt
        return new ResponseEntity<>(new QuizAttemptDTO(), HttpStatus.OK);
    }

    @PutMapping("/submitQuizAttempt/")
    public ResponseEntity<QuizAttemptDTO> submitQuizAttempt(@RequestBody QuizAttemptDTO quizAttemptDTO) {

        //update state of quizAttempt to be "submitted"
        return new ResponseEntity<>(new QuizAttemptDTO(), HttpStatus.OK);

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
            questionAttempts.add(questionAttempt);
        }
        quizAttempt.setQuestionAttempts(questionAttempts);
        return quizAttempt;
    }

    public List<QuizAttemptDTO> convertQuizAttemptsToQuizAttemptDTOs(List<QuizAttempt> quizAttempts) {

        List<QuizAttemptDTO> quizAttemptDTOS = new ArrayList<>();
        for (QuizAttempt q : quizAttempts) {
//            private Long quizAttemptId;
//            private Integer attemptCounter;
//            private Double obtainedScore;
//            private List<QuestionAttemptDTO> questionAttempts;
//            private String lastAttemptTime;
//            private Integer timeLimitRemaining;
//            private String assessmentAttemptStatusEnum; //INCOMPLETE, SUBMITTED, GRADED

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

        return quizAttemptDTO;
    }

    public List<QuestionAttemptDTO> convertQuestionAttemptsToQuestionAttemptDTOs(List<QuestionAttempt> questionAttempts) {

        List<QuestionAttemptDTO> questionAttemptDTOS = new ArrayList<>();
        for (QuestionAttempt q : questionAttempts) {
//            private Long questionAttemptId;
//            private Double questionAttemptScore;
//            private String shortAnswerResponse;
//            private QuestionDTO questionAttempted;
//            private OptionDTO optionSelected;
            questionAttemptDTOS.add(convertQuestionAttemptToQuestionAttemptDTO(q));
        }
        return questionAttemptDTOS;
    }

    public QuestionAttemptDTO convertQuestionAttemptToQuestionAttemptDTO(QuestionAttempt q) {

        QuestionAttemptDTO questionAttemptDTO = new QuestionAttemptDTO();
        questionAttemptDTO.setQuestionAttemptId(q.getQuestionAttemptId());
        questionAttemptDTO.setQuestionAttemptScore(q.getQuestionAttemptScore());
        questionAttemptDTO.setShortAnswerResponse(q.getShortAnswerResponse());
        questionAttemptDTO.setQuestionAttempted(quizController.convertQuestionToQuestionDTO(q.getQuestionAttempted()));
        questionAttemptDTO.setOptionSelected(q.getOptionSelected().getOptionContent());

        return questionAttemptDTO;
    }
}
