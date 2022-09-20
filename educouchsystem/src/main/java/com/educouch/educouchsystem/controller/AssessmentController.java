package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/assessment")
@CrossOrigin
public class AssessmentController {
    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("/assessments")
    public String addAssessment(@RequestBody Assessment assessment) {
        assessmentService.saveAssessment(assessment);
        return "New assessment has been added";
    }

    @GetMapping("/assessments")
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        List<Assessment> allAssessments = new ArrayList<Assessment>();
        if (!assessmentService.getAllAssessments().isEmpty()) {
            allAssessments = assessmentService.getAllAssessments();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allAssessments, HttpStatus.OK);
    }

    @GetMapping("/assessments/{assessmentId}")
    public ResponseEntity<Assessment> retrieveAssessmentById(@PathVariable("assessmentId") Long assessmentId) {
        try {
            Assessment existingAssessment = assessmentService.retrieveAssessmentById(assessmentId);
            return new ResponseEntity<Assessment>(existingAssessment, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Assessment>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/assessments/{assessmentId}")
    public ResponseEntity<HttpStatus> deleteAssessment(@PathVariable("assessmentId") Long assessmentId) {
        assessmentService.deleteAssessment(assessmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
