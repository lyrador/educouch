package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.ForumDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumDiscussionRepository extends JpaRepository<ForumDiscussion, Long> {
}
