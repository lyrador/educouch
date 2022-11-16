package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Reel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelRepository extends JpaRepository<Reel, Long> {

    @Query("SELECT r FROM Reel r WHERE r.courseTag.courseId=:courseId")
    List<Reel> findReelsByCourseTag(Long courseId);

    @Query("SELECT r FROM Reel r WHERE r.reelCreator.instructorId=:instructorId")
    List<Reel> findReelsByInstructorId(Long instructorId);

}
