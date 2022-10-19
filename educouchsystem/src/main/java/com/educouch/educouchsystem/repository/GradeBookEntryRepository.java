package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.GradeBookEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GradeBookEntryRepository extends CrudRepository<GradeBookEntry, Long> {
    public List<GradeBookEntry> findAll();

    @Query("SELECT g FROM GradeBookEntry g WHERE g.learnerId=:learnerId AND g.courseId=:courseId")
    public List<GradeBookEntry> findByLearnerIDAndCourseId(Long learnerId,Long courseId);
}
