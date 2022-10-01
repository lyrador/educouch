package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.courseCode=:code")
    Course findCourseByCourseCode(String code);

    @Query("SELECT c FROM Course c WHERE c.courseApprovalStatus=:courseApprovalStatusEnum")
    List<Course> findCoursesByCourseApprovalStatus(CourseApprovalStatusEnum courseApprovalStatusEnum);


}
