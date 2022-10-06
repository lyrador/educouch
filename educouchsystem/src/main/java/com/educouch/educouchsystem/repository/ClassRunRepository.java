package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.ClassRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRunRepository extends CrudRepository<ClassRun, Long> {
    @Query("SELECT r FROM ClassRun r WHERE r.course.courseId =:courseId")
    List<ClassRun> findClassRunsFromCourseId(Long courseId);

    @Query("SELECT r FROM ClassRun r WHERE r.instructor.instructorId =:instructorId")
    List<ClassRun> findClassRunsFromInstructorId(Long instructorId);

//    @Query("SELECT r FROM ClassRun r WHERE r.learner.learnerId =:learnerId")
//    List<ClassRun> findClassRunsFromLearnerId(Long learnerId);
}
