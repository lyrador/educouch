package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.DocumentSubmissionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentSubmissionAttemptRepository extends JpaRepository<DocumentSubmissionAttempt, Long> {
}
