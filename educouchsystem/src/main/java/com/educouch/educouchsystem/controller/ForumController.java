package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ForumDTO;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.ForumService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private EducatorService educatorService;

//    @PostMapping("/courses/{courseId}/forums")
//    public ResponseEntity<Forum> addForum(@PathVariable(value="courseId") Long courseId, @RequestBody Forum forumRequest) {
//        try {
//            Course course = courseService.retrieveCourseById(courseId);
//            course.getForums().add(forumRequest);
//            forumRequest.setTimestamp(LocalDateTime.now());
//            Forum forum = forumService.saveForum(forumRequest);
//            return new ResponseEntity<>(forum, HttpStatus.OK);
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/courses/{courseId}/forums")
    public ResponseEntity<Forum> addForum(@PathVariable(value="courseId") Long courseId, @RequestBody ForumDTO forumRequest) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            System.out.println(forumRequest);
            Forum newForum = new Forum();
            newForum.setForumTitle(forumRequest.getForumTitle());
            newForum.setTimestamp(LocalDateTime.now());
            //if created by learner, find the learner via learnerId and set it as the creator of forum, else it will be educator
            if (forumRequest.getCreatedByUserType().equals("LEARNER")) {
                newForum.setCreatedByLearner(learnerService.getLearnerById(forumRequest.getCreatedByUserId()));
            } else if (forumRequest.getCreatedByUserType().equals("INSTRUCTOR")) {
                newForum.setCreatedByInstructor(educatorService.findInstructorById(forumRequest.getCreatedByUserId()));
            } else if (forumRequest.getCreatedByUserType().equals("ORG_ADMIN")) {
                newForum.setCreatedByOrganisationAdmin(educatorService.findOrganisationAdminById(forumRequest.getCreatedByUserId()));
            }

            course.getForums().add(newForum);

            Forum forum = forumService.saveForum(newForum);
            return new ResponseEntity<>(forum, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/courses/{courseId}/forums")
//    public ResponseEntity<List<Forum>> getAllForumsByCourseId (@PathVariable(value="courseId") Long courseId) {
//        try {
//            Course course = courseService.retrieveCourseById(courseId);
//            List<Forum> forums = new ArrayList<Forum>();
//            forums.addAll(course.getForums());
//            return new ResponseEntity<>(forums, HttpStatus.OK);
//
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/courses/{courseId}/forums")
    public ResponseEntity<List<ForumDTO>> getAllForumsByCourseId (@PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            List<Forum> forums = new ArrayList<Forum>();
            forums.addAll(course.getForums());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<ForumDTO> forumDTOs = new ArrayList<>();
            for (Forum forum : forums) {
                ForumDTO forumDTO = new ForumDTO();
                forumDTO.setForumId(forum.getForumId());
                forumDTO.setForumTitle(forum.getForumTitle());
                forumDTO.setCreatedDateTime(forum.getTimestamp().format(formatter));
                if (forum.getCreatedByLearner() != null) {
                    forumDTO.setCreatedByUserId(forum.getCreatedByLearner().getLearnerId());
                    forumDTO.setCreatedByUserName(forum.getCreatedByLearner().getName());
                } else if (forum.getCreatedByInstructor() != null) {
                    forumDTO.setCreatedByUserId(forum.getCreatedByInstructor().getInstructorId());
                    forumDTO.setCreatedByUserName(forum.getCreatedByInstructor().getName());
                } else if (forum.getCreatedByOrganisationAdmin() != null) {
                    forumDTO.setCreatedByUserId(forum.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                    forumDTO.setCreatedByUserName(forum.getCreatedByOrganisationAdmin().getName());
                }
                forumDTOs.add(forumDTO);
            }
            return new ResponseEntity<>(forumDTOs, HttpStatus.OK);

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
//            existingForum.setForumDiscussions(forumRequest.getForumDiscussions());
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
