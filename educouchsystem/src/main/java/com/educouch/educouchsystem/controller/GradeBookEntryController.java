package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.GradeBookEntry;
import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.service.GradeBookEntryService;
import com.educouch.educouchsystem.util.exception.GradeBookEntryNotFoundException;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
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

}
