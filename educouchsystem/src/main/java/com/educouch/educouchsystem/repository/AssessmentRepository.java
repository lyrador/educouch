package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Assessment;
import com.educouch.educouchsystem.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {



}
