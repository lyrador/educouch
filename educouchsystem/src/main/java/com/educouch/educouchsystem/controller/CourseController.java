package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public String addCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return "New course has been added";
    }

    @GetMapping("/getAll")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Course> retrieveCourseById(@PathVariable("courseId") Long courseId) {
        try {
            Course existingCourse = courseService.retrieveCourseById(courseId);
            return new ResponseEntity<Course>(existingCourse, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/course/{courseId}")
    public String deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return "Deleted Course with id " + courseId; 
    }

    @PutMapping("/course/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable Long courseId) {
        try {
            Course existingCourse = courseService.retrieveCourseById(courseId);

            Course updatedCourse = courseService.saveCourse(course);
            return new ResponseEntity<Course>(updatedCourse, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }

    }


}
