package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.LearnerAttemptDTO;
import com.educouch.educouchsystem.dto.QuestionAttemptDTO;
import com.educouch.educouchsystem.model.GradeBookEntry;
import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.GradeBookEntryService;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/gradeBookEntry")
@CrossOrigin
public class GradeBookEntryController {
    @Autowired
    private GradeBookEntryService gradeBookEntryService;

    @GetMapping("/getByLearnerAndCourseId")
    public ResponseEntity<List<GradeBookEntry>> getByLearnerAndCourseId(@RequestParam Long learnerId, @RequestParam Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(gradeBookEntryService.findAllGradeBookEntriesByLearnerIdAndCourseId(learnerId,courseId));
    }

    @PostMapping("/updateEntry")
    public ResponseEntity<GradeBookEntry> updateEntry(@RequestBody GradeBookEntry gradeBookEntry)  {
        GradeBookEntry gradeBookEntry1 = null;
        try {
            gradeBookEntry1 = gradeBookEntryService.updateGradeBookEntry(gradeBookEntry);
        } catch (GradeBookEntryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find Transaction", e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(gradeBookEntry1);
    }

    @GetMapping("/getLearnerAttemptPage")
    public ResponseEntity<List<LearnerAttemptDTO>> getLearnerAttemptPage(@RequestParam Long courseId, @RequestParam Long assessmentId) {
        List<LearnerAttemptDTO> learnerAttemptDTOS = null;
        try {
            learnerAttemptDTOS = gradeBookEntryService.viewLearnerAttemptPage(courseId, assessmentId);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find course", e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(learnerAttemptDTOS);

    }

    @GetMapping("/getOpenEndedQuestions")
        public ResponseEntity<List<QuestionAttemptDTO>> getOpenEndedQuestions(@RequestParam Long learnerId, @RequestParam Long assessmentId) {
        try {
            List<QuestionAttemptDTO> list = gradeBookEntryService.getOpenEndedQns(learnerId,assessmentId);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (NoQuizAttemptsFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find quiz attempt", e);
        }


    }

    @PostMapping("/updateOpenEndedQuestions")
    public ResponseEntity<String> updateOpenEndedQuestions(@RequestParam Long learnerId, @RequestParam Long assessmentId,@RequestBody List<QuestionAttemptDTO> list) {
        try {
            gradeBookEntryService.updateOpenEndedQns(list, learnerId, assessmentId);
            return ResponseEntity.status(HttpStatus.OK).body("it worked yo");
        } catch (NoQuizAttemptsFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find quiz attempt", e);
        } catch (QuestionAttemptNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find question attempt", e);

        }
    }

}
