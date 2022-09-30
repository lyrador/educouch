package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ClassRunDTO;
import com.educouch.educouchsystem.dto.CourseDTO;
import com.educouch.educouchsystem.dto.ForumDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.OrganisationService;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/classRun")
@CrossOrigin
public class ClassRunController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private EducatorService educatorService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private ClassRunService classRunService;

//    @PostMapping("/getClassRunByCourseId/{courseId}")
//    public List<ClassRun> getClassRunByCourseId(@PathVariable Long courseId) {
//        try {
//            List<ClassRun> classRuns = courseService.retrieveClassRuns(courseId);
//            for(ClassRun c: classRuns) {
//                processClassRun(c);
//            }
//            return classRuns;
//        } catch(CourseNotFoundException ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folder not found", ex);
//        }
//    }

    @PostMapping("addToCourseId/{courseId}")
    public ResponseEntity<ClassRun> addClassRun(@PathVariable(value="courseId") Long courseId, @RequestBody ClassRunDTO classRunDTORequest) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            ClassRun classRun = classRunService.addClassRunFromCourseId(courseId, classRunDTORequest);
            return new ResponseEntity<>(classRun, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update/{classRunId}")
    public ResponseEntity<ClassRun> updateClassRun(@PathVariable("classRunId") Long classRunId, @RequestBody ClassRunDTO classRunDTO) {
        try {
            ClassRun classRun = classRunService.updateClassRun(classRunId, classRunDTO);
            return new ResponseEntity<ClassRun>(classRun, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{classRunId}")
    public ResponseEntity<HttpStatus> deleteClassRun(@PathVariable("classRunId") Long classRunId) {
        classRunService.deleteClassRun(classRunId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getClassRunsFromCourseId/{courseId}")
    public List<ClassRunDTO> findClassRunsFromCourseId(@PathVariable Long courseId) {
        List<ClassRunDTO> classRuns = classRunService.findClassRunsFromCourseId(courseId);
        return classRuns;
    }
}