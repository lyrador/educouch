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
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private CourseService courseService;





    @GetMapping("/getQuizById/{quizId}")
    public ResponseEntity<Quiz> retrieveQuizById(@PathVariable("quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<Quiz>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{quizId}/getQ")

    @DeleteMapping("/deleteAssessmentById/{assessmentId}")
    public ResponseEntity<HttpStatus> deleteAssessmentById(@PathVariable("assessmentId") Long assessmentId) {
        try {
            assessmentService.deleteAssessment(assessmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AssessmentNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAssessmentByIdFromCourseId/{assessmentId}/{courseId}")
    public ResponseEntity<HttpStatus> deleteAssessmentById(@PathVariable("assessmentId") Long assessmentId, @PathVariable("courseId") Long courseId) {
        try {
            assessmentService.deleteAssessmentFromCourseId(assessmentId, courseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AssessmentNotFoundException | CourseNotFoundException ex) {
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
}
