package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ForumDiscussionDTO;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.model.ForumDiscussion;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.ForumDiscussionService;
import com.educouch.educouchsystem.service.ForumService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/forumDiscussion")
@CrossOrigin
public class ForumDiscussionController {

    @Autowired
    private ForumDiscussionService forumDiscussionService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private EducatorService educatorService;

//    @PostMapping("/forums/{forumId}/forumDiscussions")
//    public ResponseEntity<ForumDiscussion> addForumDiscussion(@PathVariable(value="forumId") Long forumId, @RequestBody ForumDiscussion forumDiscussionRequest) {
//        try {
//            Forum forum = forumService.retrieveForumById(forumId);
//            forum.getForumDiscussions().add(forumDiscussionRequest);
//            forumDiscussionRequest.setTimestamp(LocalDateTime.now());
//            ForumDiscussion forumDiscussion = forumDiscussionService.saveForumDiscussion(forumDiscussionRequest);
//            return new ResponseEntity<>(forumDiscussion, HttpStatus.OK);
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/forums/{forumId}/forumDiscussions")
    public ResponseEntity<ForumDiscussion> addForumDiscussion(@PathVariable(value="forumId") Long forumId, @RequestBody ForumDiscussionDTO forumDiscussionRequest) {
        try {
            Forum forum = forumService.retrieveForumById(forumId);
            ForumDiscussion newForumDiscussion = new ForumDiscussion();
            newForumDiscussion.setForumDiscussionTitle(forumDiscussionRequest.getForumDiscussionTitle());
            newForumDiscussion.setForumDiscussionDescription(forumDiscussionRequest.getForumDiscussionDescription());
            newForumDiscussion.setTimestamp(LocalDateTime.now());

            //if created by learner, find the learner via learnerId and set it as the creator of forum, else it will be educator
            if (forumDiscussionRequest.getCreatedByUserType().equals("LEARNER")) {
                newForumDiscussion.setCreatedByLearner(learnerService.getLearnerById(forumDiscussionRequest.getCreatedByUserId()));
            } else if (forumDiscussionRequest.getCreatedByUserType().equals("INSTRUCTOR")) {
                newForumDiscussion.setCreatedByInstructor(educatorService.findInstructorById(forumDiscussionRequest.getCreatedByUserId()));
            } else if (forumDiscussionRequest.getCreatedByUserType().equals("ORG_ADMIN")) {
                newForumDiscussion.setCreatedByOrganisationAdmin(educatorService.findOrganisationAdminById(forumDiscussionRequest.getCreatedByUserId()));
            }

            forum.getForumDiscussions().add(newForumDiscussion);

            ForumDiscussion forumDiscussion = forumDiscussionService.saveForumDiscussion(newForumDiscussion);
            return new ResponseEntity<>(forumDiscussion, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/forums/{forumId}/forumDiscussions")
//    public ResponseEntity<List<ForumDiscussion>> getAllForumDiscussionsByForumId(@PathVariable(value="forumId") Long forumId) {
//        try {
//            Forum forum = forumService.retrieveForumById(forumId);
//            List<ForumDiscussion> forumDiscussions = new ArrayList<ForumDiscussion>();
//            forumDiscussions.addAll(forum.getForumDiscussions());
//            return new ResponseEntity<>(forumDiscussions, HttpStatus.OK);
//
//        } catch (NoSuchElementException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/forums/{forumId}/forumDiscussions")
    public ResponseEntity<List<ForumDiscussionDTO>> getAllForumDiscussionsByForumId(@PathVariable(value="forumId") Long forumId) {
        try {
            Forum forum = forumService.retrieveForumById(forumId);
            List<ForumDiscussion> forumDiscussions = new ArrayList<ForumDiscussion>();
            forumDiscussions.addAll(forum.getForumDiscussions());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<ForumDiscussionDTO> forumDiscussionDTOs = new ArrayList<>();
            for (ForumDiscussion forumDiscussion : forumDiscussions) {
                ForumDiscussionDTO forumDiscussionDTO = new ForumDiscussionDTO();
                forumDiscussionDTO.setForumDiscussionId(forumDiscussion.getForumDiscussionId());
                forumDiscussionDTO.setForumDiscussionTitle(forumDiscussion.getForumDiscussionTitle());
                forumDiscussionDTO.setForumDiscussionDescription(forumDiscussion.getForumDiscussionDescription());
                forumDiscussionDTO.setCreatedDateTime(forumDiscussion.getTimestamp().format(formatter));
                if (forumDiscussion.getCreatedByLearner() != null) {
                    forumDiscussionDTO.setCreatedByUserId(forumDiscussion.getCreatedByLearner().getLearnerId());
                    forumDiscussionDTO.setCreatedByUserName(forumDiscussion.getCreatedByLearner().getName());
                    forumDiscussionDTO.setCreatedByUserType("LEARNER");
                } else if (forumDiscussion.getCreatedByInstructor() != null) {
                    forumDiscussionDTO.setCreatedByUserId(forumDiscussion.getCreatedByInstructor().getInstructorId());
                    forumDiscussionDTO.setCreatedByUserName(forumDiscussion.getCreatedByInstructor().getName());
                    forumDiscussionDTO.setCreatedByUserType("INSTRUCTOR");
                } else if (forumDiscussion.getCreatedByOrganisationAdmin() != null) {
                    forumDiscussionDTO.setCreatedByUserId(forumDiscussion.getCreatedByOrganisationAdmin().getOrganisationAdminId());
                    forumDiscussionDTO.setCreatedByUserName(forumDiscussion.getCreatedByOrganisationAdmin().getName());
                    forumDiscussionDTO.setCreatedByUserType("ORG_ADMIN");
                }
                forumDiscussionDTOs.add(forumDiscussionDTO);
            }

            return new ResponseEntity<>(forumDiscussionDTOs, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/forumDiscussions/{forumDiscussionId}")
    public ResponseEntity<ForumDiscussion> retrieveForumDiscussionById(@PathVariable(value = "forumDiscussionId") Long forumDiscussionId) {
        try {
            ForumDiscussion existingForumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
            return new ResponseEntity<ForumDiscussion>(existingForumDiscussion, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/forumDiscussions/{forumDiscussionId}")
    public ResponseEntity<ForumDiscussion> updateForumDiscussion(@PathVariable("forumDiscussionId") Long forumDiscussionId, @RequestBody ForumDiscussion forumDiscussionRequest) {
        try {
            ForumDiscussion existingForumDiscussion = forumDiscussionService.retrieveForumDiscussionById(forumDiscussionId);
            existingForumDiscussion.setForumDiscussionTitle(forumDiscussionRequest.getForumDiscussionTitle());
            existingForumDiscussion.setForumDiscussionDescription(forumDiscussionRequest.getForumDiscussionDescription());
            return new ResponseEntity<ForumDiscussion>(forumDiscussionService.saveForumDiscussion(existingForumDiscussion), HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/forumDiscussions/{forumDiscussionId}")
    public ResponseEntity<HttpStatus> deleteForumDiscussion(@PathVariable("forumDiscussionId") Long forumDiscussionId) {
        forumDiscussionService.deleteForumDiscussion(forumDiscussionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/forums/{forumId}/forumDiscussions")
    public ResponseEntity<HttpStatus> deleteAllForumDiscussionsOfForum(@PathVariable(value="forumId") Long forumId) {
        try {
            Forum forum = forumService.retrieveForumById(forumId);
            //need to go through the list of forum discussions in forum and delete each FD
            //on top of just clearing the list of forum discussions from forum
            List<ForumDiscussion> forumDiscussionList = forum.getForumDiscussions();
            for (ForumDiscussion forumDiscussion : forumDiscussionList) {
                forumDiscussionService.deleteForumDiscussion(forumDiscussion.getForumDiscussionId());
            }
            forum.getForumDiscussions().clear();
            forumService.saveForum(forum);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
