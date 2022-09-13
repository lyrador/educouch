package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService{

    @Autowired
    private ForumRepository forumRepository;

    @Override
    public Forum saveForum(Forum forum) {
        return forumRepository.save(forum);
    }

    @Override
    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }
}
