package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ForumDiscussion;

import java.util.List;

public interface ForumDiscussionService {

    public ForumDiscussion saveForumDiscussion(ForumDiscussion forumDiscussion);

    public List<ForumDiscussion> getAllForumDiscussions();
}
