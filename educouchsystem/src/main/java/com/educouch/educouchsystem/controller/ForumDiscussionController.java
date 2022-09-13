package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.ForumDiscussion;
import com.educouch.educouchsystem.service.ForumDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forumDiscussion")
public class ForumDiscussionController {

    private ForumDiscussionService forumDiscussionService;

    @PostMapping("/add")
    public String addForumDiscussion(@RequestBody ForumDiscussion forumDiscussion) {
        forumDiscussionService.saveForumDiscussion(forumDiscussion);
        return "New Forum Discussion has been added";
    }

    @GetMapping("/getAll")
    public List<ForumDiscussion> getAllForumDiscussions() {
        return forumDiscussionService.getAllForumDiscussions();
    }


}
