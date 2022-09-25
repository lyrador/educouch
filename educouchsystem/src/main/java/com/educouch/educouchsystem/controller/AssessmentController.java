package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.FileSubmissionDTO;
import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.FileSubmission;
import com.educouch.educouchsystem.model.Quiz;
import com.educouch.educouchsystem.service.AssessmentService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.FileSubmissionService;
import com.educouch.educouchsystem.service.QuizService;
import com.educouch.educouchsystem.util.enumeration.AssessmentStatusEnum;
import com.educouch.educouchsystem.util.enumeration.FileSubmissionEnum;
import com.educouch.educouchsystem.util.exception.AssessmentNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.FileSubmissionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
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
    public ResponseEntity<FileSubmission> addFileSubmission(@RequestBody FileSubmissionDTO fileSubmissionDTO, @PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            FileSubmission newFileSubmission = new FileSubmission();
            newFileSubmission.setTitle(fileSubmissionDTO.getAssessmentTitle());
            newFileSubmission.setDescription(fileSubmissionDTO.getAssessmentDescription());
            newFileSubmission.setMaxScore(fileSubmissionDTO.getAssessmentMaxScore());

            if (fileSubmissionDTO.getAssessmentFileSubmissionEnum().equals("INDIVIDUAL")) {
                newFileSubmission.setFileSubmissionEnum(FileSubmissionEnum.INDIVIDUAL);
            } else if (fileSubmissionDTO.getAssessmentFileSubmissionEnum().equals("GROUP")) {
                newFileSubmission.setFileSubmissionEnum(FileSubmissionEnum.GROUP);
            }

            if (fileSubmissionDTO.getAssessmentIsOpen().equals("true")) {
                newFileSubmission.setOpen(Boolean.TRUE);
            } else if (fileSubmissionDTO.getAssessmentIsOpen().equals("false")) {
                newFileSubmission.setOpen(Boolean.FALSE);
            }

            if (fileSubmissionDTO.getAssessmentStatusEnum().equals("PENDING")) {
                newFileSubmission.setAssessmentStatus(AssessmentStatusEnum.PENDING);
            } else if (fileSubmissionDTO.getAssessmentStatusEnum().equals("INCOMPLETE")) {
                newFileSubmission.setAssessmentStatus(AssessmentStatusEnum.INCOMPLETE);
            } else if (fileSubmissionDTO.getAssessmentStatusEnum().equals("COMPLETE")) {
                newFileSubmission.setAssessmentStatus(AssessmentStatusEnum.COMPLETE);
            }

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date)formatter.parse(fileSubmissionDTO.getAssessmentStartDate());
            Date endDate = (Date)formatter.parse(fileSubmissionDTO.getAssessmentEndDate());
            newFileSubmission.setStartDate(startDate);
            newFileSubmission.setEndDate(endDate);

            fileSubmissionService.saveFileSubmission(courseId, newFileSubmission);
            return new ResponseEntity<>(newFileSubmission, HttpStatus.OK);
        } catch (CourseNotFoundException | ParseException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllFileSubmissionsByCourseId/{courseId}")
    public ResponseEntity<List<FileSubmissionDTO>> getAllFileSubmissionsByCourseId (@PathVariable(value="courseId") Long courseId) {
        try {
            List<FileSubmission> fileSubmissions = fileSubmissionService.getAllFileSubmissionByCourseId(courseId);
            List<FileSubmissionDTO> fileSubmissionDTOs = new ArrayList<>();
            for (FileSubmission filesubmission : fileSubmissions) {
                FileSubmissionDTO fileSubmissionDTO = new FileSubmissionDTO();
                fileSubmissionDTO.setAssessmentId(filesubmission.getAssessmentId());
                fileSubmissionDTO.setAssessmentTitle(filesubmission.getTitle());
                fileSubmissionDTO.setAssessmentDescription(filesubmission.getDescription());
                fileSubmissionDTO.setAssessmentMaxScore(filesubmission.getMaxScore());

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                fileSubmissionDTO.setAssessmentStartDate(formatter.format(filesubmission.getStartDate()));
                fileSubmissionDTO.setAssessmentEndDate(formatter.format(filesubmission.getEndDate()));

                fileSubmissionDTOs.add(fileSubmissionDTO);
            }
            return new ResponseEntity<>(fileSubmissionDTOs, HttpStatus.OK);

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

    @DeleteMapping("/deleteAssessmentById/{assessmentId}")
    public ResponseEntity<HttpStatus> deleteAssessmentById(@PathVariable("assessmentId") Long assessmentId) {
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
