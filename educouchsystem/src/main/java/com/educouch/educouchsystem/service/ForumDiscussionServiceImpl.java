package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ForumDiscussion;
import com.educouch.educouchsystem.repository.ForumDiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumDiscussionServiceImpl implements ForumDiscussionService{

    @Autowired
    private ForumDiscussionRepository forumDiscussionRepository;

    @Override
    public ForumDiscussion saveForumDiscussion(ForumDiscussion forumDiscussion) {
        return forumDiscussionRepository.save(forumDiscussion);
    }

    @Override
    public List<ForumDiscussion> getAllForumDiscussions() {

        return forumDiscussionRepository.findAll();
    }

    @Override
    public ForumDiscussion retrieveForumDiscussionById(Long id) {
        return forumDiscussionRepository.findById(id).get();
    }

    @Override
    public void deleteForumDiscussion(Long id) {
        forumDiscussionRepository.deleteById(id);
    }
}
