package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.AssessmentService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.FileSubmissionService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;
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
    private FileSubmissionService fileSubmissionService;

//    @Autowired
//    private QuizService quizService;
//
//    @PostMapping("/addNewQuiz/{courseId}")
//    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz, @PathVariable(value="courseId") Long courseId) {
//        try {
//            Course course = courseService.retrieveCourseById(courseId);
//            quizService.saveQuiz(courseId, quiz);
//            return new ResponseEntity<>(quiz, HttpStatus.OK);
//        } catch (CourseNotFoundException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/addNewFileSubmission/{courseId}")
    public ResponseEntity<FileSubmission> addFileSubmission(@RequestBody FileSubmission fileSubmission, @PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            fileSubmissionService.saveFileSubmission(courseId, fileSubmission);
            return new ResponseEntity<>(fileSubmission, HttpStatus.OK);
        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllFileSubmissionsByCourseId/{courseId}")
    public ResponseEntity<List<FileSubmission>> getAllFileSubmissionsByCourseId (@PathVariable(value="courseId") Long courseId) {
        try {
            List<FileSubmission> fileSubmissions = fileSubmissionService.getAllFileSubmissionByCourseId(courseId);
            return new ResponseEntity<>(fileSubmissions, HttpStatus.OK);

        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getFileSubmissionById/{fileSubmissionId}")
    public ResponseEntity<FileSubmission> retrieveFileSubmissionById(@PathVariable("fileSubmissionId") Long fileSubmissionId) {
        try {
            FileSubmission fileSubmission = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);
            return new ResponseEntity<FileSubmission>(fileSubmission, HttpStatus.OK);
        } catch (FileSubmissionNotFoundException ex) {
            return new ResponseEntity<FileSubmission>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteFileSubmissionById/{fileSubmissionId}")
    public ResponseEntity<HttpStatus> deleteFileSubmissionById(@PathVariable("fileSubmissionId") Long fileSubmissionId) {
        try {
            fileSubmissionService.deleteFileSubmission(fileSubmissionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FileSubmissionNotFoundException ex) {
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

    @PutMapping("/updateFileSubmission/{fileSubmissionId}")
    public ResponseEntity<FileSubmission> updateFileSubmission(@RequestBody FileSubmission fileSubmission, @PathVariable("fileSubmissionId") Long fileSubmissionId) {
        try {
            FileSubmission fileSubmissionToUpdate = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);
            fileSubmissionService.updateFileSubmission(fileSubmissionToUpdate, fileSubmission);
            return new ResponseEntity<FileSubmission>(fileSubmissionToUpdate, HttpStatus.OK);
        } catch (FileSubmissionNotFoundException ex) {
            return new ResponseEntity<FileSubmission>(HttpStatus.NOT_FOUND);
        }
    }
}
