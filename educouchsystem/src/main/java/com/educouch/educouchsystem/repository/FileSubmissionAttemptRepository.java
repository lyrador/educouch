package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import com.educouch.educouchsystem.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileSubmissionAttemptRepository extends JpaRepository<FileSubmissionAttempt, Long> {
    @Query("SELECT q FROM FileSubmissionAttempt q WHERE q.learner.learnerId=:learnerId")
    List<FileSubmissionAttempt> findFileSubmissionAttemptsByLearnerId(Long learnerId);
}
