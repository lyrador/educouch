package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Educator;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EducatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private EducatorService educatorService;

    @PostMapping("{instructorId}/courses")
    public ResponseEntity<Course> addCourse(@PathVariable(value="instructorId") Long instructorId, @RequestBody Course courseRequest) {
        try{
            Instructor instructor = educatorService.findInstructorById(instructorId);
            if (instructor.getCourses() == null) {
                List<Course> courseList = new ArrayList<>();
                instructor.setCourses(courseList);
            }
            instructor.getCourses().add(courseRequest);
            Course course = courseService.saveCourse(courseRequest);
            if (course.getInstructors() == null) {
                List<Instructor> instructorList = new ArrayList<>();
                course.setInstructors(instructorList);
            }
            course.getInstructors().add(instructor);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/instructors/{instructorId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesByInstructorId (@PathVariable(value="instructorId") Long instructorId) {
        try {
            Instructor instructor = educatorService.findInstructorById(instructorId);
            List<Course> courses = new ArrayList<>();
            courses.addAll(instructor.getCourses());

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
