package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
