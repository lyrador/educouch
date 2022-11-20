package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.CourseStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseStatsRepository extends JpaRepository<CourseStats, Long> {

    @Query("SELECT c FROM CourseStats c WHERE c.courseId=:courseId")
    CourseStats findCourseStatsByCourseId(Long courseId);

    List<CourseStats> findAll();
}
