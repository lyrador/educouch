package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;

import java.util.List;

public interface CourseService {
    public Course saveCourse(Course course);
    public List<Course> getAllCourses();

    public Course getCourseById(Long courseId) throws CourseNotFoundException;

    public Course getCourseByCourseCode(String courseCode);
    public Course retrieveCourseById(Long id);
    public void deleteCourse(Long id);

    public void submitCourseForApproval(Long courseId) throws CourseNotFoundException;

    public void approveCourse(Long courseId) throws CourseNotFoundException;

    public void rejectCourse(Long courseId, String rejectionReason) throws CourseNotFoundException;

    public void publishCourse(Long courseId) throws CourseNotFoundException;
    public List<Course> getCoursesByCourseApprovalStatus(String courseApprovalStatus);

    public List<ClassRun> retrieveClassRuns(Long courseId) throws CourseNotFoundException;

    public void addClassRunToCourse(Long courseId, ClassRun classRun) throws CourseNotFoundException;

    public boolean checkIfStudentIsEnrolledInACourse(Long studentId, Long courseId) throws CourseNotFoundException,
            LearnerNotFoundException;

}
