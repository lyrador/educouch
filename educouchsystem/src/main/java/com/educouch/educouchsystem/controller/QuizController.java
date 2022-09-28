package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.AssessmentService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.enumeration.AssessmentStatusEnum;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @PostMapping("/addNewQuiz/{courseId}")
    public ResponseEntity<Quiz> addQuiz(@RequestBody QuizDTO quizDTO, @PathVariable(value="courseId") Long courseId) {
        try {
            Quiz newQuiz = new Quiz();
            newQuiz.setTitle(quizDTO.getAssessmentTitle());
            newQuiz.setDescription(quizDTO.getAssessmentDescription());
            newQuiz.setMaxScore(quizDTO.getAssessmentMaxScore());

            if (quizDTO.getAssessmentIsOpen().equals("true")) {
                newQuiz.setOpen(Boolean.TRUE);
            } else if (quizDTO.getAssessmentIsOpen().equals("false")) {
                newQuiz.setOpen(Boolean.FALSE);
            }

            if (quizDTO.getAssessmentHasTimeLimit().equals("true")) {
                newQuiz.setHasTimeLimit(Boolean.TRUE);
            } else if (quizDTO.getAssessmentHasTimeLimit().equals("false")) {
                newQuiz.setHasTimeLimit(Boolean.FALSE);
            }

            if (quizDTO.getAssessmentIsAutoRelease().equals("true")) {
                newQuiz.setAutoRelease(Boolean.TRUE);
            } else if (quizDTO.getAssessmentIsAutoRelease().equals("false")) {
                newQuiz.setAutoRelease(Boolean.FALSE);
            }

            if (quizDTO.getAssessmentStatusEnum().equals("PENDING")) {
                newQuiz.setAssessmentStatus(AssessmentStatusEnum.PENDING);
            } else if (quizDTO.getAssessmentStatusEnum().equals("INCOMPLETE")) {
                newQuiz.setAssessmentStatus(AssessmentStatusEnum.INCOMPLETE);
            } else if (quizDTO.getAssessmentStatusEnum().equals("COMPLETE")) {
                newQuiz.setAssessmentStatus(AssessmentStatusEnum.COMPLETE);
            }

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date)formatter.parse(quizDTO.getAssessmentStartDate());
            Date endDate = (Date)formatter.parse(quizDTO.getAssessmentEndDate());
            newQuiz.setStartDate(startDate);
            newQuiz.setEndDate(endDate);

            quizService.saveQuiz(courseId, newQuiz);
            return new ResponseEntity<>(newQuiz, HttpStatus.OK);
        } catch (CourseNotFoundException | ParseException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getQuizById/{quizId}")
    public ResponseEntity<Quiz> retrieveQuizById(@PathVariable("quizId") Long quizId) {
        try {
            Quiz quiz = quizService.retrieveQuizById(quizId);
            return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<Quiz>(HttpStatus.NOT_FOUND);
        }
    }

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
