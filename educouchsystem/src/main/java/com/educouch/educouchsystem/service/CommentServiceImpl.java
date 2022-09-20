package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Comment;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.repository.CommentRepository;
import com.educouch.educouchsystem.repository.ForumDiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment retrieveCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


}
