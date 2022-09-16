package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Forum;

import java.util.List;

public interface ForumService {

    public Forum saveForum(Forum forum);

    public List<Forum> getAllForums();

    public Forum retrieveForumById(Long id);

    public void deleteForum(Long id);
}
