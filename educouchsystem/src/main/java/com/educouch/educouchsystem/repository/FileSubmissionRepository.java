package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.FileSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSubmissionRepository extends JpaRepository<FileSubmission, Long> {
}
