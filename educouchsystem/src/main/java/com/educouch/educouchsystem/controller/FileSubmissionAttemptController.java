package com.educouch.educouchsystem.controller;
import com.educouch.educouchsystem.dto.*;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.FileSubmissionService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.QuizAttemptService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.enumeration.AssessmentAttemptStatusEnum;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;
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
@RequestMapping("/fileSubmissionAttempt")
@CrossOrigin
public class FileSubmissionAttemptController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizController quizController;

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private FileSubmissionService fileSubmissionService;



    @PostMapping("/createFileSubmissionAttempt/{assessmentId}/{learnerId}")
    public ResponseEntity<FileSubmissionAttemptDTO> createFileSubmissionAttempt(@PathVariable(value = "assessmentId") Long assessmentId,
                                                            @PathVariable(value = "learnerId") Long learnerId) {
        try {
            FileSubmission fileSubmission = fileSubmissionService.retrieveFileSubmissionById(assessmentId);
            Learner learner = learnerService.getLearnerById(learnerId);
            FileSubmissionAttempt fileSubmissionAttempt = initializeFileSubmissionAttempt(fileSubmission);
            //save quizAttempt
            fileSubmissionAttempt = fileSubmissionService.saveFileSubmissionAttempt(fileSubmissionAttempt);
            FileSubmissionAttemptDTO fileSubmissionAttemptDTO = convertFileSubmissionAttemptToFileSubmissionAttemptDTO(fileSubmissionAttempt);
            return new ResponseEntity<FileSubmissionAttemptDTO>(fileSubmissionAttemptDTO, HttpStatus.OK);
        } catch (FileSubmissionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public FileSubmissionAttempt initializeFileSubmissionAttempt(FileSubmission fileSubmission) {
        FileSubmissionAttempt fileSubmissionAttempt = new FileSubmissionAttempt();
        fileSubmissionAttempt.setFileSubmissionAttempted(fileSubmission);
        fileSubmissionAttempt.setObtainedScore(0.0);
        return fileSubmissionAttempt;
    }

    public FileSubmissionAttemptDTO convertFileSubmissionAttemptToFileSubmissionAttemptDTO(FileSubmissionAttempt fileSubmissionAttempt) {
        FileSubmissionAttemptDTO fileSubmissionAttemptDTO = new FileSubmissionAttemptDTO();
        fileSubmissionAttemptDTO.setObtainedScore(fileSubmissionAttempt.getObtainedScore());
        fileSubmissionAttemptDTO.setFileSubmissionAttemptId(fileSubmissionAttempt.getFileSubmissionAttemptId());
        return fileSubmissionAttemptDTO; //file submission attempted not set
    }

    public FileSubmissionDTO convertFileSubmissionToFileSubmissionDTO(FileSubmission fileSubmission) {
        FileSubmissionDTO fileSubmissionDTO = new FileSubmissionDTO();
        fileSubmissionDTO.setAssessmentId(fileSubmission.getAssessmentId());
        fileSubmissionDTO.setAssessmentDescription(fileSubmission.getDescription());
        //unfinished
        return new FileSubmissionDTO();
    }
}
