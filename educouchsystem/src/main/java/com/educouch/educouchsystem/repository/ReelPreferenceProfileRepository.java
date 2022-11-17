package com.educouch.educouchsystem.repository;
import com.educouch.educouchsystem.model.ReelPreferenceProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelPreferenceProfileRepository extends JpaRepository<ReelPreferenceProfile, Long> {

    @Query("SELECT r FROM ReelPreferenceProfile r WHERE r.learner.learnerId=:learnerId")
    List<ReelPreferenceProfile> findReelPreferenceProfileByLearnerId(Long learnerId);}
