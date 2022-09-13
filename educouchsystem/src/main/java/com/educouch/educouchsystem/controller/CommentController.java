package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Comment;
import com.educouch.educouchsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestBody Comment comment) {
        commentService.saveComment(comment);
        return "New Comment has been added";
    }

    @GetMapping("/getAll")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

}
