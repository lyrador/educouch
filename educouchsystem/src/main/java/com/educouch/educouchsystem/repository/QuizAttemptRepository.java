package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    @Query("SELECT q FROM QuizAttempt q WHERE q.learner.learnerId=:learnerId")
    List<QuizAttempt> findQuizAttemptsByLearnerId(Long learnerId);
}
