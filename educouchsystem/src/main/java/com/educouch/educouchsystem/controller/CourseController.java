package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.webServiceModel.CourseRejectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    public String addCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return "New course has been added";
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> allCourses = new ArrayList<Course>();
        if (!courseService.getAllCourses().isEmpty()) {
            allCourses = courseService.getAllCourses();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> retrieveCourseById(@PathVariable("courseId") Long courseId) {
        try {
            Course existingCourse = courseService.retrieveCourseById(courseId);
            return new ResponseEntity<Course>(existingCourse, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("courseId") Long courseId) {
        try {
            Course existingCourse = courseService.retrieveCourseById(courseId);
            existingCourse.setCourseDescription(course.getCourseDescription());
            existingCourse.setCourseTitle(course.getCourseTitle());
            existingCourse.setCourseTimeline(course.getCourseTimeline());
            existingCourse.setCourseCode(course.getCourseCode());
            existingCourse.setAgeGroup(course.getAgeGroup());
            existingCourse.setCourseApprovalStatus(course.getCourseApprovalStatus());
            existingCourse.setCourseMaxScore(course.getCourseMaxScore());
            courseService.saveCourse(existingCourse);

            return new ResponseEntity<Course>(existingCourse, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/courses/submitCourseForApproval/{courseId}")
    public String submitCourseForApproval(@PathVariable("courseId") Long courseId) {
        try {
            courseService.submitCourseForApproval(courseId);

            return "Course has been successfully submitted for approval.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }

    @GetMapping("/courses/approveCourse/{courseId}")
    public String approveCourse(@PathVariable("courseId") Long courseId) {
        try {
            courseService.approveCourse(courseId);

            return "Course has been approved.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }

    @PostMapping("/courses/rejectCourse")
    public String rejectCourse(CourseRejectionModel courseRejectionModel) {
        try {
            Long courseId = courseRejectionModel.getCourseId();
            String rejectionReason = courseRejectionModel.getRejectionReason();

            courseService.rejectCourse(courseId, rejectionReason);

            return "Course has been rejected.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }






}
