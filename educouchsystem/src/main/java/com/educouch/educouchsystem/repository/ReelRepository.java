package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Reel;
import com.educouch.educouchsystem.util.enumeration.ReelApprovalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelRepository extends JpaRepository<Reel, Long> {

    @Query("SELECT r FROM Reel r WHERE r.courseTag.courseId=:courseId and r.reelApprovalStatusEnum=:statusEnum")
    public List<Reel> findReelsByCourseTag(Long courseId, ReelApprovalStatusEnum statusEnum);

    @Query("SELECT r FROM Reel r WHERE r.reelCreator.instructorId=:instructorId")
    public List<Reel> findReelsByInstructorId(Long instructorId);

    @Query("SELECT r FROM Reel r WHERE r.reelApprovalStatusEnum=:statusEnum ORDER BY r.reelTimeStamp ")
    public List<Reel> findAllReelsChronologically(ReelApprovalStatusEnum statusEnum);

}
