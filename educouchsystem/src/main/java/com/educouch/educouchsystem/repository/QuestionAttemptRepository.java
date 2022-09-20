package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.QuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
}
