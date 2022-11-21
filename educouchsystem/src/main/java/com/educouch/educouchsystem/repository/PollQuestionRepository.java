package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.PollQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollQuestionRepository extends JpaRepository<PollQuestion, Long> {
}
