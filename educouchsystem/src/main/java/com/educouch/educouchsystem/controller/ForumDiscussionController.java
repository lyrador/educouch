package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.model.ForumDiscussion;
import com.educouch.educouchsystem.service.ForumDiscussionService;
import com.educouch.educouchsystem.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/forumDiscussion")
public class ForumDiscussionController {

    @Autowired
    private ForumDiscussionService forumDiscussionService;

    @Autowired
    private ForumService forumService;

    @PostMapping("/forums/{forumId}/forumDiscussions")
    public ResponseEntity<ForumDiscussion> addForumDiscussion(@PathVariable(value="forumId") Long forumId, @RequestBody ForumDiscussion forumDiscussionRequest) {
        try {
            Forum forum = forumService.retrieveForumById(forumId);
            forum.getForumDiscussions().add(forumDiscussionRequest);
            ForumDiscussion forumDiscussion = forumDiscussionService.saveForumDiscussion(forumDiscussionRequest);
            return new ResponseEntity<>(forumDiscussion, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/forums/{forumId}/forumDiscussions")
    public ResponseEntity<List<ForumDiscussion>> getAllForumDiscussionsByForumId(@PathVariable(value="forumId") Long forumId) {
        try {
            Forum forum = forumService.retrieveForumById(forumId);
            List<ForumDiscussion> forumDiscussions = new ArrayList<ForumDiscussion>();
            forumDiscussions.addAll(forum.getForumDiscussions());
            return new ResponseEntity<>(forumDiscussions, HttpStatus.OK);

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
