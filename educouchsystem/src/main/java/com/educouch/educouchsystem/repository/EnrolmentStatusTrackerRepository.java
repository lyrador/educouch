package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.EnrolmentStatusTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolmentStatusTrackerRepository extends JpaRepository<EnrolmentStatusTracker, Long> {
}
