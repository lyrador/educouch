package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.AssessmentDTO;
import com.educouch.educouchsystem.dto.FileSubmissionDTO;
import com.educouch.educouchsystem.dto.QuizDTO;
import com.educouch.educouchsystem.model.*;
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
import com.sun.mail.iap.Response;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private QuizService quizService;

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

                if (filesubmission.getFileSubmissionEnum() == FileSubmissionEnum.INDIVIDUAL) {
                    fileSubmissionDTO.setAssessmentFileSubmissionEnum("INDIVIDUAL");
                } else if (filesubmission.getFileSubmissionEnum() == FileSubmissionEnum.GROUP) {
                    fileSubmissionDTO.setAssessmentFileSubmissionEnum("GROUP");
                }

                if (filesubmission.getOpen()) {
                    fileSubmissionDTO.setAssessmentIsOpen("true");
                } else if (filesubmission.getOpen() == false) {
                    fileSubmissionDTO.setAssessmentIsOpen("false");
                }

                if (filesubmission.getAssessmentStatus() == AssessmentStatusEnum.PENDING) {
                    fileSubmissionDTO.setAssessmentStatusEnum("PENDING");
                } else if (filesubmission.getAssessmentStatus() == AssessmentStatusEnum.INCOMPLETE) {
                    fileSubmissionDTO.setAssessmentStatusEnum("INCOMPLETE");
                } else if (filesubmission.getAssessmentStatus() == AssessmentStatusEnum.COMPLETE) {
                    fileSubmissionDTO.setAssessmentStatusEnum("COMPLETE");
                }

                fileSubmissionDTOs.add(fileSubmissionDTO);
            }
            return new ResponseEntity<>(fileSubmissionDTOs, HttpStatus.OK);

        } catch (CourseNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllAssessmentsByCourseId")
    public ResponseEntity<List<AssessmentDTO>> getAllAssessmentsByCourseId(@RequestParam String courseId) {

        try {
            List<Assessment> assessments = new ArrayList<>();
            assessments = assessmentService.getAllAssessmentsByCourseId(Long.parseLong(courseId));
            List<AssessmentDTO> assessmentDTOS = setAssessmentDTOList(assessments);

            return new ResponseEntity<List<AssessmentDTO>>(assessmentDTOS, HttpStatus.OK);
        } catch (CourseNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/releaseAssessment/{assessmentId}")
    public ResponseEntity<Assessment> releaseAssessment(@PathVariable(value="assessmentId") Long assessmentId) {
        try {
            Assessment a = assessmentService.retrieveAssessmentById(assessmentId);
            a.setOpen(true);
            assessmentService.saveAssessment(a);
            return new ResponseEntity<>(a,HttpStatus.OK);
        } catch (AssessmentNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getAllQuizzesByCourseId/{courseId}")
    public ResponseEntity<List<QuizDTO>> getAllQuizzesByCourseId(@PathVariable(value="courseId") Long courseId) {
        try {
            List<Quiz> quizzes = quizService.getAllQuizzesByCourseId(courseId);
            List<QuizDTO> quizDTOs = new ArrayList<>();
            for (Quiz quiz : quizzes) {
                QuizDTO quizDTO = new QuizDTO();
                quizDTO.setAssessmentId(quiz.getAssessmentId());
                quizDTO.setAssessmentTitle(quiz.getTitle());
                quizDTO.setAssessmentDescription(quiz.getDescription());
                quizDTO.setAssessmentMaxScore(quiz.getMaxScore());

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                quizDTO.setAssessmentStartDate(formatter.format(quiz.getStartDate()));
                quizDTO.setAssessmentEndDate(formatter.format(quiz.getEndDate()));

                if (quiz.getOpen()) {
                    quizDTO.setAssessmentIsOpen("true");
                } else if (quiz.getOpen() == false) {
                    quizDTO.setAssessmentIsOpen("false");
                }

                if (quiz.getHasTimeLimit()) {
                    quizDTO.setHasTimeLimit("true");
                } else if (quiz.getHasTimeLimit() == false) {
                    quizDTO.setHasTimeLimit("false");
                }

                quizDTO.setTimeLimit(quiz.getTimeLimit());

                if (quiz.getAutoRelease()) {
                    quizDTO.setIsAutoRelease("true");
                } else if (quiz.getAutoRelease() == false) {
                    quizDTO.setIsAutoRelease("false");
                }

                if (quiz.getAssessmentStatus() == AssessmentStatusEnum.PENDING) {
                    quizDTO.setAssessmentStatusEnum("PENDING");
                } else if (quiz.getAssessmentStatus() == AssessmentStatusEnum.INCOMPLETE) {
                    quizDTO.setAssessmentStatusEnum("INCOMPLETE");
                } else if (quiz.getAssessmentStatus() == AssessmentStatusEnum.COMPLETE) {
                    quizDTO.setAssessmentStatusEnum("COMPLETE");
                }

                quizDTOs.add(quizDTO);
            }
            return new ResponseEntity<>(quizDTOs, HttpStatus.OK);

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

    @PutMapping("/updateFileSubmission/{fileSubmissionId}")
    public ResponseEntity<FileSubmission> updateFileSubmission(@RequestBody FileSubmissionDTO fileSubmissionDTO, @PathVariable("fileSubmissionId") Long fileSubmissionId) {
        try {
            FileSubmission fileSubmissionToUpdate = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);

            fileSubmissionToUpdate.setTitle(fileSubmissionDTO.getAssessmentTitle());
            fileSubmissionToUpdate.setDescription(fileSubmissionDTO.getAssessmentDescription());
            fileSubmissionToUpdate.setMaxScore(fileSubmissionDTO.getAssessmentMaxScore());

            if (fileSubmissionDTO.getAssessmentFileSubmissionEnum().equals("INDIVIDUAL")) {
                fileSubmissionToUpdate.setFileSubmissionEnum(FileSubmissionEnum.INDIVIDUAL);
            } else if (fileSubmissionDTO.getAssessmentFileSubmissionEnum().equals("GROUP")) {
                fileSubmissionToUpdate.setFileSubmissionEnum(FileSubmissionEnum.GROUP);
            }

            if (fileSubmissionDTO.getAssessmentIsOpen().equals("true")) {
                fileSubmissionToUpdate.setOpen(Boolean.TRUE);
            } else if (fileSubmissionDTO.getAssessmentIsOpen().equals("false")) {
                fileSubmissionToUpdate.setOpen(Boolean.FALSE);
            }

            if (fileSubmissionDTO.getAssessmentStatusEnum().equals("PENDING")) {
                fileSubmissionToUpdate.setAssessmentStatus(AssessmentStatusEnum.PENDING);
            } else if (fileSubmissionDTO.getAssessmentStatusEnum().equals("INCOMPLETE")) {
                fileSubmissionToUpdate.setAssessmentStatus(AssessmentStatusEnum.INCOMPLETE);
            } else if (fileSubmissionDTO.getAssessmentStatusEnum().equals("COMPLETE")) {
                fileSubmissionToUpdate.setAssessmentStatus(AssessmentStatusEnum.COMPLETE);
            }

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date)formatter.parse(fileSubmissionDTO.getAssessmentStartDate());
            Date endDate = (Date)formatter.parse(fileSubmissionDTO.getAssessmentEndDate());
            fileSubmissionToUpdate.setStartDate(startDate);
            fileSubmissionToUpdate.setEndDate(endDate);

            fileSubmissionService.updateFileSubmission(fileSubmissionToUpdate, fileSubmissionToUpdate);
            return new ResponseEntity<FileSubmission>(fileSubmissionService.saveFileSubmission(fileSubmissionToUpdate), HttpStatus.OK);
        } catch (FileSubmissionNotFoundException ex) {
            return new ResponseEntity<FileSubmission>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updateQuiz/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody QuizDTO quizDTO, @PathVariable("quizId") Long quizId) {
        try {
            Quiz quizToUpdate = quizService.retrieveQuizById(quizId);

            quizToUpdate.setTitle(quizDTO.getAssessmentTitle());
            quizToUpdate.setDescription(quizDTO.getAssessmentDescription());
            quizToUpdate.setMaxScore(quizDTO.getAssessmentMaxScore());

            if (quizDTO.getAssessmentIsOpen().equals("true")) {
                quizToUpdate.setOpen(Boolean.TRUE);
            } else if (quizDTO.getAssessmentIsOpen().equals("false")) {
                quizToUpdate.setOpen(Boolean.FALSE);
            }

            if (quizDTO.getHasTimeLimit().equals("true")) {
                quizToUpdate.setHasTimeLimit(Boolean.TRUE);
            } else if (quizDTO.getHasTimeLimit().equals("false")) {
                quizToUpdate.setHasTimeLimit(Boolean.FALSE);
            }

            quizToUpdate.setTimeLimit(quizDTO.getTimeLimit());

            if (quizDTO.getIsAutoRelease().equals("true")) {
                quizToUpdate.setAutoRelease(Boolean.TRUE);
            } else if (quizDTO.getIsAutoRelease().equals("false")) {
                quizToUpdate.setAutoRelease(Boolean.FALSE);
            }

            if (quizDTO.getAssessmentStatusEnum().equals("PENDING")) {
                quizToUpdate.setAssessmentStatus(AssessmentStatusEnum.PENDING);
            } else if (quizDTO.getAssessmentStatusEnum().equals("INCOMPLETE")) {
                quizToUpdate.setAssessmentStatus(AssessmentStatusEnum.INCOMPLETE);
            } else if (quizDTO.getAssessmentStatusEnum().equals("COMPLETE")) {
                quizToUpdate.setAssessmentStatus(AssessmentStatusEnum.COMPLETE);
            }

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date)formatter.parse(quizDTO.getAssessmentStartDate());
            Date endDate = (Date)formatter.parse(quizDTO.getAssessmentEndDate());
            quizToUpdate.setStartDate(startDate);
            quizToUpdate.setEndDate(endDate);

            quizService.updateQuiz(quizToUpdate, quizToUpdate);
            return new ResponseEntity<Quiz>(quizService.saveQuiz(quizToUpdate), HttpStatus.OK);
        } catch (QuizNotFoundException ex) {
            return new ResponseEntity<Quiz>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    List<AssessmentDTO> setAssessmentDTOList (List<Assessment> assessments) {

        List<AssessmentDTO> assessmentDTOS = new ArrayList<>();
        for(Assessment a : assessments) {

            AssessmentDTO dtoItem = new AssessmentDTO();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            dtoItem.setAssessmentId(a.getAssessmentId());
            dtoItem.setTitle(a.getTitle());
            dtoItem.setDescription(a.getDescription());
            dtoItem.setMaxScore(a.getMaxScore());
            dtoItem.setStartDate(formatter.format(a.getStartDate()));
            dtoItem.setEndDate(formatter.format(a.getEndDate()));
            if(a.getOpen()) {
                dtoItem.setOpen("true");
            } else {
                dtoItem.setOpen("false");
            }
            dtoItem.setAssessmentStatus(a.getAssessmentStatus());
            String s = a.getClass().getName();
            String[] assessmentTypeArray = s.split("\\.");
//            System.out.println(assessmentTypeArray[4]);
            dtoItem.setAssessmentType(assessmentTypeArray[4]);
            assessmentDTOS.add(dtoItem);
        }

        return assessmentDTOS;
    }
}
