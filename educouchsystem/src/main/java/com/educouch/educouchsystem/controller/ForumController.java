package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping("/add")
    public String addForum(@RequestBody Forum forum) {
        forumService.saveForum(forum);
        return "New Forum has been added";
    }

    @GetMapping("/getAll")
    public List<Forum> getAllForums() {
        return forumService.getAllForums();
    }
}
