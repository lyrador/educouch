package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.DocumentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentSubmissionRepository extends JpaRepository<DocumentSubmission, Long> {
}
