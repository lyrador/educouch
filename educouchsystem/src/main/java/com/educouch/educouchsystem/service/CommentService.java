package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Comment;

import java.util.List;

public interface CommentService {

    public Comment saveComment(Comment comment);

    public List<Comment> getAllComments();

    public Comment retrieveCommentById(Long id);

    public void deleteComment(Long id);
}
