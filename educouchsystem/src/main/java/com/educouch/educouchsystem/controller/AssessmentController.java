package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.AssessmentService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
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

    @Autowired
    private QuizService quizService;

    @PostMapping("/addNewQuiz/{courseId}")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz, @PathVariable(value="courseId") Long courseId) {
        Course course = courseService.retrieveCourseById(courseId);
        try {
            List<Assessment> assessments = new ArrayList<>();
            assessments.add(quiz);
            course.setAssessments(assessments);
            courseService.saveCourse(course);
            quizService.saveQuiz(courseId, quiz);
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllAssessmentsByCourseId/{courseId}")
    public ResponseEntity<List<Assessment>> getAllAssessmentsByCourseId (@PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            List<Assessment> assessments = new ArrayList<Assessment>();
            assessments.addAll(course.getAssessments());
            return new ResponseEntity<>(assessments, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAssessmentById/{assessmentId}")
    public ResponseEntity<Assessment> retrieveAssessmentById(@PathVariable("assessmentId") Long assessmentId) {
        try {
            Assessment assessment = assessmentService.retrieveAssessmentById(assessmentId);
            return new ResponseEntity<Assessment>(assessment, HttpStatus.OK);
        } catch (AssessmentNotFoundException ex) {
            return new ResponseEntity<Assessment>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAssessmentById/{assessmentId}")
    public ResponseEntity<HttpStatus> deleteAssessment(@PathVariable("assessmentId") Long assessmentId) {
        try {
            assessmentService.deleteAssessment(assessmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AssessmentNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAllAssessmentsByCourseId/{courseId}")
    public ResponseEntity<HttpStatus> deleteAllAssessmentsOfCourse(@PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            List<Assessment> assessments = course.getAssessments();
            for (Assessment assessment : assessments) {
                assessmentService.deleteAssessment(assessment.getAssessmentId());
            }
            course.getAssessments().clear();
            courseService.saveCourse(course);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException | AssessmentNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/updateQuiz")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz) {
        try {
            Quiz toUpdateQuiz = quizService.updateQuiz(quiz);
            return new ResponseEntity<Quiz>(toUpdateQuiz, HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<Quiz>(HttpStatus.NOT_FOUND);
        }
    }
}
