package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.AssessmentService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
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

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{courseId}/assessments")
    public ResponseEntity<Assessment> addAssessment(@PathVariable(value="courseId") Long courseId, @RequestBody Assessment assessment) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            course.getAssessments().add(assessment);
            Assessment addedAssessment = assessmentService.saveAssessment(assessment);
            return new ResponseEntity<>(addedAssessment, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses/{courseId}/assessments")
    public ResponseEntity<List<Assessment>> getAllAssessmentsByCourseId() {
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
        } catch (AssessmentNotFoundException ex) {
            return new ResponseEntity<Assessment>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/assessments/{assessmentId}")
    public ResponseEntity<HttpStatus> deleteAssessment(@PathVariable("assessmentId") Long assessmentId) {
        try {
            assessmentService.deleteAssessment(assessmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AssessmentNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/assessments/{assessmentId}")
    public ResponseEntity<Assessment> updateAssessment(@RequestBody Assessment assessment, @PathVariable("assessmentId") Long assessmentId) {
        try {
            Assessment toUpdateAssessment = assessmentService.retrieveAssessmentById(assessmentId);
            toUpdateAssessment.setTitle(assessment.getTitle());
            toUpdateAssessment.setDescription(assessment.getDescription());
            toUpdateAssessment.setMaxScore(assessment.getMaxScore());
            toUpdateAssessment.setStartDate(assessment.getStartDate());
            toUpdateAssessment.setEndDate(assessment.getEndDate());
            toUpdateAssessment.setOpen(assessment.getOpen());
            toUpdateAssessment.setAssessmentStatus(assessment.getAssessmentStatus());
            return new ResponseEntity<Assessment>(toUpdateAssessment, HttpStatus.OK);
        } catch (NoSuchElementException | AssessmentNotFoundException ex) {
            return new ResponseEntity<Assessment>(HttpStatus.NOT_FOUND);
        }
    }
}
