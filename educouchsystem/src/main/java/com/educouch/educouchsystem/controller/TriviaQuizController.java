package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.GameDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.TriviaQuizService;
import com.educouch.educouchsystem.util.exception.TriviaQuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/triviaQuiz")
@CrossOrigin
public class TriviaQuizController {

    @Autowired
    private TriviaQuizService triviaQuizService;

    @Autowired
    private ClassRunService classRunService;

    @PostMapping("/classRun/{classRunId}/triviaQuiz")
    public ResponseEntity<TriviaQuiz> addTriviaQuiz(@PathVariable(value="classRunId") Long classRunId, @RequestBody TriviaQuiz triviaQuizRequest) {
        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        triviaQuizRequest.setClassRun(classRun);
        if (classRun.getTriviaQuizzes() != null) {
            classRun.getTriviaQuizzes().add(triviaQuizRequest);
        } else {
            List<TriviaQuiz> classRunTriviaQuizzes = new ArrayList<>();
            classRunTriviaQuizzes.add(triviaQuizRequest);
            classRun.setTriviaQuizzes(classRunTriviaQuizzes);
        }

        triviaQuizRequest.setCreationDate(new Date());
        TriviaQuiz triviaQuiz = triviaQuizService.saveTriviaQuiz(triviaQuizRequest);
        return new ResponseEntity<>(triviaQuiz, HttpStatus.OK);
    }

    @GetMapping("/triviaQuizzes")
    public ResponseEntity<List<TriviaQuiz>> getAllTriviaQuizzes() {
        List<TriviaQuiz> allTriviaQuizzes = new ArrayList<>();
        if (!triviaQuizService.getAllTriviaQuizzes().isEmpty()) {
            allTriviaQuizzes = triviaQuizService.getAllTriviaQuizzes();

//            for (TriviaQuiz triviaQuiz : allTriviaQuizzes) {
//                processClassRun(triviaQuiz.getClassRun());
//            }

            return new ResponseEntity<>(allTriviaQuizzes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{triviaQuizId}/triviaQuizzes")
    public ResponseEntity<TriviaQuiz> getTriviaQuizById(@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);

           // processClassRun(existingTriviaQuiz.getClassRun());

            return new ResponseEntity<>(existingTriviaQuiz, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/triviaQuizzes/{triviaQuizId}")
    public ResponseEntity<TriviaQuiz> deleteTriviaQuiz(@PathVariable("triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            ClassRun classRun = existingTriviaQuiz.getClassRun();
            classRun.getTriviaQuizzes().remove(existingTriviaQuiz);
            existingTriviaQuiz.setClassRun(null);
            triviaQuizService.deleteTriviaQuiz(triviaQuizId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TriviaQuizNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/triviaQuizzes/{triviaQuizId}")
    public ResponseEntity<TriviaQuiz> updateTriviaQuiz(@RequestBody TriviaQuiz triviaQuiz, @PathVariable(value = "triviaQuizId") Long triviaQuizId) {
        try {
            TriviaQuiz existingTriviaQuiz = triviaQuizService.getTriviaQuizById(triviaQuizId);
            existingTriviaQuiz.setTriviaQuizTitle(triviaQuiz.getTriviaQuizTitle());
            existingTriviaQuiz.setTriviaQuizDescription(triviaQuiz.getTriviaQuizDescription());
            existingTriviaQuiz.setNumOfQuestions(triviaQuiz.getNumOfQuestions());
            //existingTriviaQuiz.setTriviaQuestions(triviaQuiz.getTriviaQuestions());
            triviaQuizService.saveTriviaQuiz(existingTriviaQuiz);

           // processClassRun(existingTriviaQuiz.getClassRun());

            return new ResponseEntity<>(existingTriviaQuiz, HttpStatus.OK);
        } catch (TriviaQuizNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/triviaQuizzesFromClassRun/{classRunId}")
    public ResponseEntity<List<TriviaQuiz>> getAllTriviaQuizzesByClassRunId (@PathVariable(value = "classRunId") Long classRunId) {
        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        List<TriviaQuiz> triviaQuizzes = new ArrayList<>();
        triviaQuizzes.addAll(classRun.getTriviaQuizzes());

        //unmarshalling
//        for (TriviaQuiz triviaQuiz : triviaQuizzes) {
//            triviaQuiz.getClassRun().setEvents(null);
//            triviaQuiz.getClassRun().setEnrolledLearners(null);
//            triviaQuiz.getClassRun().setEnrolmentStatusTrackers(null);
//            triviaQuiz.getClassRun().setLearnerTransactions(null);
//            triviaQuiz.getClassRun().setCourse(null);
//            triviaQuiz.getClassRun().setTriviaQuizzes(null);
//        }

        return new ResponseEntity<>(triviaQuizzes, HttpStatus.OK);
    }

    @GetMapping("/getAllPollsAndTriviaFromClassRun/{classRunId}")
    public ResponseEntity<List<GameDTO>> getAllPollsAndTriviaFromClassRunId (@PathVariable(value = "classRunId") Long classRunId) {
        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        List<GameDTO> gameDTOs = new ArrayList<>();
        for (TriviaQuiz trivia : classRun.getTriviaQuizzes()) {
            GameDTO gameDTO = new GameDTO(trivia.getTriviaQuizId(), trivia.getTriviaQuizDescription(), trivia.getNumOfQuestions(), "TRIVIA");
            gameDTOs.add(gameDTO);
        }
        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }

//don't really need all this unmarshalling when we use json ignore
    private void processClassRun(ClassRun c) {
//        List<EnrolmentStatusTracker> enrolmentStatusTrackers = c.getEnrolmentStatusTrackers();
//        for(EnrolmentStatusTracker e: enrolmentStatusTrackers) {
//            e.setClassRun(null);
//            e.setLearner(null);
//        }

        c.setCalendar(null);
        c.setEvents(null);
        c.setTriviaQuizzes(null);
//        Instructor i = c.getInstructor();
//        if(i != null) {
//            i.setClassRuns(null);
//            i.setOrganisation(null);
//            i.setCourses(null);
//        }


        Course course = c.getCourse();
        course.setFolders(null);
        course.setClassRuns(null);
        course.setFolders(null);
        course.setAssessments(null);
//        course.setInstructors(null);
        course.setOrganisation(null);

        c.setEnrolledLearners(null);
        c.setEnrolmentStatusTrackers(null);
        c.setLearnerTransactions(null);
    }
}
