package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.model.Forum;
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
            for(Course c: allCourses) {
                processCourse(c);
            }
            return new ResponseEntity<>(allCourses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private Course processCourse(Course c) {
        List<Forum> forums = c.getForums();
        for(Forum f: forums) {
            f.setForumDiscussions(null);
            f.setCourse(null);
            f.setLearners(null);
            f.setEducators(null);
        }

        List<Folder> childFolders = c.getFolders();
        for(Folder f: childFolders) {
            processFolder(f);
        }

        return c;
    }

    private Folder processFolder(Folder folder) {
        List<Folder> subFolders = folder.getChildFolders();
//        for(Folder cf: subFolders) {
//            cf.setParentFolder(null);
//            cf.setAttachments(null);
//            cf.setChildFolders(null);
//            cf.setCourse(null);
//        }
        Folder parentFolder = folder.getParentFolder();
        if(parentFolder != null) {
            parentFolder.setChildFolders(null);
            parentFolder.setAttachments(null);
            parentFolder.setParentFolder(null);
            parentFolder.setCourse(null);
        }
        folder.setCourse(null);
        folder.getAttachments();

        return folder;

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
    public String rejectCourse(@RequestBody CourseRejectionModel courseRejectionModel) {
        try {
            System.out.println("React A");
            Long courseId = courseRejectionModel.getCourseId();
            System.out.println("Course ID is " + courseId.toString());
            System.out.println("React b");
            String rejectionReason = courseRejectionModel.getRejectionReason();
            System.out.println("Rejection reason is " + rejectionReason);
            System.out.println("React c");

            courseService.rejectCourse(courseId, rejectionReason);

            return "Course has been rejected.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }






}
