package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.EnrolmentStatusTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolmentStatusTrackerRepository extends JpaRepository<EnrolmentStatusTracker, Long> {

    @Query("SELECT e FROM EnrolmentStatusTracker e WHERE e.learner.learnerId =:learnerId")
    List<EnrolmentStatusTracker> retrieveEnrolmentStatusTrackerByLearnerId(Long learnerId);

    @Query("SELECT e FROM EnrolmentStatusTracker e WHERE e.learner.learnerId =:learnerId AND e.classRun.classRunId = :classRunId ")
    List<EnrolmentStatusTracker> retrieveEnrolmentStatusTrackerByLearnerIdAndClassRunId(Long learnerId, Long classRunId);
}
