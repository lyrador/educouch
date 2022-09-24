package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSubmissionAttemptRepository extends JpaRepository<FileSubmissionAttempt, Long> {
}
