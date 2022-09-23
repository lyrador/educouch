package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/forum")
@CrossOrigin
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{courseId}/forums")
    public ResponseEntity<Forum> addForum(@PathVariable(value="courseId") Long courseId, @RequestBody Forum forumRequest) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            course.getForums().add(forumRequest);
            forumRequest.setTimestamp(LocalDateTime.now());
            Forum forum = forumService.saveForum(forumRequest);
            return new ResponseEntity<>(forum, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses/{courseId}/forums")
    public ResponseEntity<List<Forum>> getAllForumsByCourseId (@PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            List<Forum> forums = new ArrayList<Forum>();
            forums.addAll(course.getForums());
            return new ResponseEntity<>(forums, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/forums/{forumId}")
    public ResponseEntity<Forum> retrieveForumById(@PathVariable(value = "forumId") Long forumId) {
        try {
            Forum existingForum = forumService.retrieveForumById(forumId);
            return new ResponseEntity<Forum>(existingForum, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/forums/{forumId}")
    public ResponseEntity<Forum> updateForum(@PathVariable("forumId") Long forumId, @RequestBody Forum forumRequest) {
        try {
            Forum existingForum = forumService.retrieveForumById(forumId);
            existingForum.setForumTitle(forumRequest.getForumTitle());
            existingForum.setForumDiscussions(forumRequest.getForumDiscussions());
            return new ResponseEntity<Forum>(forumService.saveForum(existingForum), HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/forums/{forumId}")
    public ResponseEntity<HttpStatus> deleteForum(@PathVariable("forumId") Long forumId) {
        forumService.deleteForum(forumId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/courses/{courseId}/forums")
    public ResponseEntity<HttpStatus> deleteAllForumsOfCourse(@PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            List<Forum> forumList = course.getForums();
            for (Forum forum : forumList) {
                forumService.deleteForum(forum.getForumId());
            }
            course.getForums().clear();
            courseService.saveCourse(course);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



}
